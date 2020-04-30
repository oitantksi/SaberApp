/*
 * This file is part of SaberApp.
 *
 *     SaberApp is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     SaberApp is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with SaberApp.  If not, see <https://www.gnu.org/licenses/>.
 */

package omega.isaacbenito.saberapp.authentication.impl

import android.accounts.Account
import android.accounts.AccountManager
import android.content.Context
import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.work.*
import kotlinx.coroutines.*
import omega.isaacbenito.saberapp.authentication.*
import omega.isaacbenito.saberapp.authentication.AuthResult.Error
import omega.isaacbenito.saberapp.authentication.AuthResult.Success
import omega.isaacbenito.saberapp.authentication.AuthState.LoggedIn
import omega.isaacbenito.saberapp.authentication.AuthState.LoggedOut
import omega.isaacbenito.saberapp.data.entities.User
import omega.isaacbenito.saberapp.utils.NetworkUtils
import timber.log.Timber
import java.net.ConnectException
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Gestiona la autenticació de l'usuari.
 *
 * Es crea una única instància per a tota la aplicació.
 *
 * @author Isaac Benito
 * @param context
 */
@Singleton
open class AuthenticationManagerImpl @Inject constructor(
    val context: Context
) : AuthenticationManager {

    private val AUTH_TOKEN_TYPE = "SABER_APP_USER"

    companion object {
        const val ARG_ACCOUNT_TYPE = "ACCOUNT_TYPE"
        const val ARG_AUTH_TYPE = "AUTH_TYPE"
        const val ARG_ACCOUNT_NAME = "ACCOUNT_NAME"
        const val ARG_IS_ADDING_NEW_ACCOUNT = "IS_ADDING_ACCOUNT"
    }

    private var _accountName: String? = null

    @Inject
    lateinit var serverAuthenticate: ServerAuthenticate

    @Inject
    lateinit var networkUtils: NetworkUtils

    private val _accountManager = AccountManager.get(context)

    private var _authToken: String? = null

    private val _loginState = MutableLiveData<AuthResult>()

    private val _authResult = MutableLiveData<AuthResult>()

    private val _authState = MutableLiveData<AuthState>()

    override fun login(userMail: String, password: String): LiveData<AuthResult> {
        if (!AccountGlobals.isValidEmail(
                userMail
            ) or !AccountGlobals.isValidPassword(
                password
            )
        ) {
            _loginState.value = Error(Error
                .WRONG_CREDENTIALS_ERROR)
            _authState.value = LoggedOut
        } else if (!networkUtils.isNetworkConnected()) {
            Timber.d("No network connection")
            _loginState.value = Error(Error
                .NO_INTERNET_ACCESS)
            _authState.value = LoggedOut
        } else {
            Timber.d("Performing server login")
            runBlocking {
                _loginState.value = withContext(Dispatchers.IO) {
                    return@withContext serverLogin(
                        User.AuthCredentials(
                            userMail,
                            password
                        )
                    )
                }
            }
            when (_loginState.value) {
                is Success -> _authState.value = LoggedIn
                else -> _authState.value = LoggedOut
            }
        }

        return _loginState
    }

    internal suspend fun serverLogin(userCredentials: User.AuthCredentials): AuthResult {
        try {
            val response = serverAuthenticate.logInUser(userCredentials)
            return if (response.isSuccessful) {
                _authToken = response.headers()["Authorization"].toString()
                val userMail = userCredentials.email
                Timber.d(
                    "Authentication success. User $userMail logged in and obtained token $_authToken"
                )
                addUserToDeviceAccounts(userMail, userCredentials.password)
                userJustLoggedIn(userMail)
                Success
            } else {
                Timber.d("Authentication not successfull")
                Error(Error
                    .WRONG_CREDENTIALS_ERROR)
            }
        } catch (e: ConnectException) {
            Timber.d("Authentication ConnectException message ${e.message}")
            return Error(Error
                .SERVER_UNREACHABLE_ERROR)
        }
    }

    override fun register(
        user_name: String,
        user_surname: String,
        user_nickname: String,
        email: String,
        password: String,
        centre: String
    ): LiveData<AuthResult> {

        runBlocking {
            try {
                val response = serverAuthenticate.registerUser(
                    User.Dto(
                        user_name, user_surname, user_nickname, email, password,
                        centre, 'A'
                    )
                )

                if (response.isSuccessful) {
                    _authResult.value = serverLogin(
                        User.AuthCredentials(
                            email,
                            password
                        )
                    )
                    when(_authResult.value) {
                        is Success -> _authState.value = LoggedIn
                        else -> _authState.value = LoggedOut
                    }
                } else {
                    _authState.value = LoggedOut
                    _authResult.value = Error(Error.WRONG_CREDENTIALS_ERROR)
                }

            } catch (e: ConnectException) {
                _authState.value = LoggedOut
                _authResult.value = Error(Error.SERVER_UNREACHABLE_ERROR)
            }
        }

        return _authResult
    }

    private fun addUserToDeviceAccounts(userMail: String, password: String) {
        val account = Account(userMail,
            AccountGlobals.ACCOUNT_TYPE
        )
        _accountManager.addAccountExplicitly(account, password, null)
        _accountManager.setAuthToken(account, AUTH_TOKEN_TYPE, _authToken)
    }

    @Suppress("deprecation")
    private fun removeUserFromDeviceAccounts(userMail: String) {
        val account = Account(userMail, AccountGlobals.ACCOUNT_TYPE)
        if (Build.VERSION.SDK_INT >= 22) {
            _accountManager.removeAccount(account, null, null, null)
        } else {
            _accountManager.removeAccount(account, null, null)
        }
    }

    override fun getAuthToken(): String {
        val account = _accountManager.getAccountsByType(AccountGlobals.ACCOUNT_TYPE)
            .asIterable().first()
        return _accountManager.peekAuthToken(account, AUTH_TOKEN_TYPE)
    }

    override fun logout() = logout(_accountName ?: throw NullPointerException())

    override fun logout(accountName: String) {
        _authState.value = LoggedOut
        removeUserFromDeviceAccounts(accountName)
    }

    override fun unregister() = unregister(_accountName ?: throw NullPointerException())

    override fun unregister(accountName: String): LiveData<AuthResult> {
        CoroutineScope(Dispatchers.Main).launch {
            _authResult.value = withContext(Dispatchers.IO) {
                try {
                    val response = serverAuthenticate.unregisterUser(accountName)
                    return@withContext if (response.isSuccessful) {
                        logout(accountName)
                        Success
                    } else {
                        Error(Error
                            .WRONG_CREDENTIALS_ERROR)
                    }
                } catch (e: ConnectException) {
                    return@withContext Error(Error
                        .SERVER_UNREACHABLE_ERROR)
                }

            }

        }

        return _authResult
    }

    override fun userIsLoggedIn(): Boolean {
        if (_authState.value == LoggedIn) {
            return true
        }

        try {
            val account = getUserAccount()
            performServerLoginOnDeviceAuthenticatedUser(account)
            _accountName = account.name
            _authState.value = LoggedIn
            return true
        } catch (e: NullPointerException) {
            _authState.value = LoggedOut
            return false
        }
    }

    private fun performServerLoginOnDeviceAuthenticatedUser(account: Account) {
        val userMail = account.name
        val password = _accountManager.getPassword(account)
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {

                if (networkUtils.isNetworkConnected()) {
                    serverLogin(User.AuthCredentials(userMail, password))
                } else {
                    val workerConstraints = Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .build()

                    val loginWork = OneTimeWorkRequestBuilder<AuthWorker>()
                        .setConstraints(workerConstraints)
                        .setBackoffCriteria(
                            BackoffPolicy.LINEAR,
                            OneTimeWorkRequest.MIN_BACKOFF_MILLIS,
                            TimeUnit.MILLISECONDS)
                        .setInputData(workDataOf(
                            AuthWorker.USER_EMAIL to userMail,
                            AuthWorker.USER_PASSWORD to password))
                        .build()

                    WorkManager.getInstance(context).enqueueUniqueWork(
                        AuthWorker.AUTH_WORK,
                        ExistingWorkPolicy.REPLACE,
                        loginWork
                    )
                }
            }
        }
    }

    private fun userJustLoggedIn(email: String) {
        if (_authToken != null) {
            _authState.postValue(LoggedIn)
            _accountName = email
            serverAuthenticate.setAuthToken(_authToken!!)
        }
    }

    override fun updateUserAccountIdentifier(newUserMail: String) : LiveData<AuthResult>{
        val result = MutableLiveData<AuthResult>()

        val oldUserAccount = getUserAccount()

        val password = _accountManager.getPassword(oldUserAccount)

        addUserToDeviceAccounts(newUserMail, password)

        removeUserFromDeviceAccounts(oldUserAccount.name)

        return result.apply { value = Success}
    }

    override fun updateUserPassword(oldPassword: String, newUserPassword: String) : LiveData<AuthResult>{
        val result = MutableLiveData<AuthResult>()

        val userAccount = getUserAccount()

        if (oldPassword != _accountManager.getPassword(userAccount)) {
            return result.apply { value = Error(Error.WRONG_CREDENTIALS_ERROR)}
        }

        _accountManager.setPassword(userAccount, newUserPassword)

        return result.apply { value = Success}
    }

    override fun getUserAccountIdentifier(): String = _accountName ?: throw NullPointerException()

    private fun getUserAccount() : Account =
        _accountManager.getAccountsByType(AccountGlobals.ACCOUNT_TYPE).asIterable().firstOrNull()
            ?: throw NullPointerException()
}