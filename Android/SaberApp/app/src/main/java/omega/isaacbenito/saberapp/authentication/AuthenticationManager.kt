package omega.isaacbenito.saberapp.authentication

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import omega.isaacbenito.saberapp.authentication.login.LoginError
import omega.isaacbenito.saberapp.authentication.login.LoginState
import omega.isaacbenito.saberapp.authentication.login.LoginSuccess
import omega.isaacbenito.saberapp.authentication.registration.RegistrationError
import omega.isaacbenito.saberapp.authentication.registration.RegistrationState
import omega.isaacbenito.saberapp.authentication.registration.RegistrationSuccesful
import omega.isaacbenito.saberapp.server.model.UserCredentials
import omega.isaacbenito.saberapp.server.model.UserDto
import retrofit2.Response
import javax.inject.Inject

class AuthenticationManager @Inject constructor()  {

    companion object {
        const val ARG_ACCOUNT_TYPE = "ACCOUNT_TYPE"
        const val ARG_AUTH_TYPE = "AUTH_TYPE"
        const val ARG_ACCOUNT_NAME = "ACCOUNT_NAME"
        const val ARG_IS_ADDING_NEW_ACCOUNT = "IS_ADDING_ACCOUNT"
    }

    @Inject lateinit var serverAuthenticate : ServerAuthenticate

    var authJob = Job()
    val authScope = CoroutineScope(authJob + Dispatchers.Main )

    fun isUserLoggedIn() : Boolean {return false}

    private var authToken: String? = null
        get() = authToken

    private val _loginState = MutableLiveData<LoginState>()
    val loginState : LiveData<LoginState>
        get() = _loginState

    fun loginUser(userMail: String, password: String) : LiveData<LoginState>  {
       authScope.launch {
           val response = serverAuthenticate.logInUser(UserCredentials(userMail, password))
           if (response.isSuccessful) {
               authToken = response.headers().get("Authorization").toString()
               _loginState.value = LoginSuccess
           } else {
               _loginState.value = LoginError("Unable to log in")
           }
       }

        return loginState
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
            _registrationState.value = RegistrationError("email not valid")
            return registrationState
        }

        if (!isPasswordValid(password)) {
            _registrationState.value = RegistrationError("password not valid")
            return registrationState
        }

        authScope.launch {
            val response = serverAuthenticate.registerUser(UserDto(user_name, user_surname, user_nickname,
                email, password, centre, 'A'))

            if (response.isSuccessful) {
                loginUser(email, password)
                _registrationState.value = RegistrationSuccesful
            } else {
                _registrationState.value = RegistrationError("")
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