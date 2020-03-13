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

package omega.isaacbenito.saberapp.authentication

import android.accounts.Account
import android.accounts.AccountManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import omega.isaacbenito.saberapp.api.entities.UserCredentials
import omega.isaacbenito.saberapp.api.entities.UserDto
import omega.isaacbenito.saberapp.authentication.ui.AuthError
import omega.isaacbenito.saberapp.authentication.ui.AuthState
import omega.isaacbenito.saberapp.authentication.ui.AuthSuccess
import omega.isaacbenito.saberapp.di.UserComponent
import omega.isaacbenito.saberapp.utils.NetworkUtils
import java.net.ConnectException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Gestiona la autenticació de l'usuari.
 *
 * Es crea una única instància per a tota la aplicació.
 *
 * @property userComponentFactory
 * @constructor
 * TODO
 *
 * @param context
 */
@Singleton
class AuthenticationManager @Inject constructor(
    context: Context,
    private val userComponentFactory: UserComponent.Factory
) {

    private val _tag = this.javaClass.name

    companion object {
        const val ARG_ACCOUNT_TYPE = "ACCOUNT_TYPE"
        const val ARG_AUTH_TYPE = "AUTH_TYPE"
        const val ARG_ACCOUNT_NAME = "ACCOUNT_NAME"
        const val ARG_IS_ADDING_NEW_ACCOUNT = "IS_ADDING_ACCOUNT"
    }

    var userComponent: UserComponent? = null
        private set

    @Inject
    lateinit var serverAuthenticate: ServerAuthenticate

    @Inject
    lateinit var networkUtils: NetworkUtils

    private val accountManager = AccountManager.get(context)

    private var authToken: String? = null

    lateinit var userMail: String
        private set

    private val _loginState = MutableLiveData<AuthState>()

    fun login(userMail: String, password: String): MutableLiveData<AuthState> {
        if (!AccountGlobals.isValidEmail(userMail) or !AccountGlobals.isValidPassword(password)) {
            _loginState.value = AuthError(AuthError.WRONG_CREDENTIALS_ERROR)
        } else if (!networkUtils.isConnected) {
            _loginState.value = AuthError(AuthError.NO_INTERNET_ACCESS)
        } else {
            runBlocking {
                _loginState.value = withContext(Dispatchers.IO) {
                    return@withContext serverLogin(
                        UserCredentials(
                            userMail,
                            password
                        )
                    )
                }
            }
        }

        return _loginState
    }

    private suspend fun serverLogin(userCredentials: UserCredentials): AuthState {
        try {
            val response = serverAuthenticate.logInUser(userCredentials)
            return if (response.isSuccessful) {
                authToken = response.headers()["Authorization"].toString()
                userMail = userCredentials.email
                Log.d(
                    _tag,
                    "Authentication success. User $userMail logged in and obtained token $authToken"
                )
                addUser(userMail, userCredentials.password)
                userJustLoggedIn()
                AuthSuccess
            } else {
                Log.d(_tag, "Authentication not successfull")
                AuthError(AuthError.WRONG_CREDENTIALS_ERROR)
            }
        } catch (e: ConnectException) {
            Log.d(_tag, "Authentication ConnectException message ${e.message}")
            return AuthError(AuthError.SERVER_UNREACHABLE_ERROR)
        }
    }

    private val _authState = MutableLiveData<AuthState>()
    private val authState: LiveData<AuthState>
        get() = _authState

    fun registerUser(
        user_name: String,
        user_surname: String,
        user_nickname: String,
        email: String,
        password: String,
        centre: String
    ): LiveData<AuthState> {

        runBlocking {
            try {
                val response = serverAuthenticate.registerUser(
                    UserDto(
                        user_name, user_surname, user_nickname, email, password,
                        centre, 'A'
                    )
                )

                if (response.isSuccessful) {
                    _authState.value = serverLogin(
                        UserCredentials(
                            email,
                            password
                        )
                    )
                } else {
                    _authState.value = AuthError(AuthError.WRONG_CREDENTIALS_ERROR)
                }

            } catch (e: ConnectException) {
                _authState.value = AuthError(AuthError.SERVER_UNREACHABLE_ERROR)
            }
        }

        return authState
    }

    private fun addUser(userMail: String, password: String) {
        val account = Account(userMail, AccountGlobals.ACCOUNT_TYPE)
        accountManager.addAccountExplicitly(account, password, null)
    }

    private fun removeUser(userMail: String) {
        val account = Account(userMail, AccountGlobals.ACCOUNT_TYPE)
        if (Build.VERSION.SDK_INT >= 22) {
            accountManager.removeAccount(account, null, null, null)
        } else {
            accountManager.removeAccount(account, null, null)
        }
    }

    fun getAuthToken(): String {
        return if (userIsLoggedIn() && authToken != null) {
            authToken!!
        } else {
            ""
        }
    }

    fun logoutUser(): LiveData<AuthState> {
        removeUser(userMail)
        userComponent = null
        _authState.value = AuthSuccess
        return authState
    }

    fun unregisterUser(): LiveData<AuthState> {
        CoroutineScope(Dispatchers.Main).launch {
            _authState.value = withContext(Dispatchers.IO) {
                try {
                    val response = serverAuthenticate.unregisterUser(userMail)
                    return@withContext if (response.isSuccessful) {
                        logoutUser()
                        AuthSuccess
                    } else {
                        AuthError(AuthError.WRONG_CREDENTIALS_ERROR)
                    }
                } catch (e: ConnectException) {
                    return@withContext AuthError(AuthError.SERVER_UNREACHABLE_ERROR)
                }

            }

        }

        return authState
    }

    fun userIsLoggedIn(): Boolean {
        if (userComponent != null) {
            return true
        }

        val accounts = accountManager.getAccountsByType(AccountGlobals.ACCOUNT_TYPE)
        if (accounts.isEmpty()) {
            return false
        }

        userMail = accounts[0].name
        val password = accountManager.getPassword(accounts[0])

        login(userMail, password)

        return true
    }

    private fun userJustLoggedIn() {
        userComponent = userComponentFactory.create()
        serverAuthenticate.setAuthToken(authToken!!)
    }


}