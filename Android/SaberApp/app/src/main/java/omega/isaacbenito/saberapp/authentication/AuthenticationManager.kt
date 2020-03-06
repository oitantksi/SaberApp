package omega.isaacbenito.saberapp.authentication

import android.accounts.Account
import android.accounts.AccountManager
import android.content.Context
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import omega.isaacbenito.saberapp.NetworkUtils
import omega.isaacbenito.saberapp.authentication.ui.*
import omega.isaacbenito.saberapp.server.model.UserCredentials
import omega.isaacbenito.saberapp.server.model.UserDto
import java.net.ConnectException
import javax.inject.Inject

class AuthenticationManager @Inject constructor(context: Context)  {

    companion object {
        const val ACCOUNT_TYPE = "omega.saberapp"
        const val ARG_ACCOUNT_TYPE = "ACCOUNT_TYPE"
        const val ARG_AUTH_TYPE = "AUTH_TYPE"
        const val ARG_ACCOUNT_NAME = "ACCOUNT_NAME"
        const val ARG_IS_ADDING_NEW_ACCOUNT = "IS_ADDING_ACCOUNT"
    }

    @Inject lateinit var serverAuthenticate : ServerAuthenticate

    @Inject lateinit var networkUtils: NetworkUtils

    private val accountManager = AccountManager.get(context)

    private var authJob = Job()
    private val authScope = CoroutineScope(authJob + Dispatchers.Main )

    fun isUserLoggedIn() : Boolean {return false}

    private var authToken: String? = null
        get() = authToken

    private val _loginState = MutableLiveData<LoginState>()
    val loginState : LiveData<LoginState>
        get() = _loginState

    fun addUser(userMail: String, password: String) : MutableLiveData<LoginState> {
        authScope.launch {
            val result = withContext(Dispatchers.IO) {
                return@withContext login(UserCredentials(userMail, password))}
            if (result is LoginSuccess) {
                val account = Account(userMail, ACCOUNT_TYPE )
                accountManager.addAccountExplicitly(account, password, null);
            }
            _loginState.postValue(result)
        }

        return _loginState
    }

    suspend fun login(userCredentials: UserCredentials) : LoginState {
        try {
            val response = serverAuthenticate.logInUser(userCredentials)
            if (response.isSuccessful) {
                authToken = response.headers().get("Authorization").toString()
                return LoginSuccess
            } else {
                return WrongCredentials
            }
        } catch (e: ConnectException) {
            return ServerUnreachable
        }
    }




    private val _registrationState = MutableLiveData<RegistrationState>()
    val registrationState : LiveData<RegistrationState>
        get() = _registrationState

    fun registerUser(user_name: String,
                     user_surname: String,
                     user_nickname: String,
                     email: String,
                     password: String,
                     centre: String,
                     curs: String,
                     aula: String) : LiveData<RegistrationState> {
        if (!isUserMailValid(email)) {
            _registrationState.value =
                RegistrationError("email not valid")
            return registrationState
        }

        if (!isPasswordValid(password)) {
            _registrationState.value =
                RegistrationError("password not valid")
            return registrationState
        }

        authScope.launch {
            val response = serverAuthenticate.registerUser(UserDto(user_name, user_surname, user_nickname,
                email, password, centre, 'A'))

            if (response.isSuccessful) {
                addUser(email, password)
                _registrationState.value =
                    RegistrationSuccesful
            } else {
                _registrationState.value =
                    RegistrationError(
                        ""
                    )
            }
        }

        return registrationState
    }

    fun logoutUser() {}

    fun unregisterUser() {}

    /**
     * Comprova si el email proporcionat per l'usuari és una cadena de text amb format email correcta
     *
     * @param userMail email proporcionat per l'usuari
     * @return Boolean
     */
    private fun isUserMailValid(userMail: String): Boolean {
        return if (userMail.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(userMail).matches()
        } else {
            userMail.isNotBlank()
        }
    }

    /**
     * Comprova si la contrassenya proporcionada per l'usuari compleix les característiques necessàries
     *
     * @param password
     * @return
     */
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }

}