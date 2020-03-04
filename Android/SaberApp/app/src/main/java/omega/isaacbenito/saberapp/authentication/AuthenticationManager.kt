package omega.isaacbenito.saberapp.authentication

import android.util.Log
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import omega.isaacbenito.saberapp.authentication.login.LoginError
import omega.isaacbenito.saberapp.authentication.login.LoginState
import omega.isaacbenito.saberapp.authentication.login.LoginSuccess
import omega.isaacbenito.saberapp.server.model.UserCredentials
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import javax.inject.Inject

class AuthenticationManager @Inject constructor()  {

    companion object {
        const val ARG_ACCOUNT_TYPE = "ACCOUNT_TYPE"
        const val ARG_AUTH_TYPE = "AUTH_TYPE"
        const val ARG_ACCOUNT_NAME = "ACCOUNT_NAME"
        const val ARG_IS_ADDING_NEW_ACCOUNT = "IS_ADDING_ACCOUNT"
    }

    @Inject lateinit var serverAuthenticate : ServerAuthenticate

    fun isUserLoggedIn() : Boolean {return false}

    private lateinit var authToken: String

    private val _loginState = MutableLiveData<LoginState>()
    val loginState : LiveData<LoginState>
        get() = _loginState

    fun loginUser(userMail: String, password: String) : LiveData<LoginState>  {
        serverAuthenticate.logInUser(UserCredentials(userMail, password)).enqueue(
            object : Callback<Unit> {
                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    if(response.isSuccessful) {
                        authToken = response.headers().get("Authorization").toString()
                        _loginState.value = LoginSuccess
                    } else {
                        _loginState.value = LoginError("Unable to log in")
                    }
                }

                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    _loginState.value = LoginError("Unable to log in")
                }
            }
        )
        return loginState
    }

    fun setUserToken(authToken: String) {}

    fun getAuthToken(): String {return ""}

    fun registerUser(user_name: String,
                     user_surname: String,
                     user_nickname: String,
                     email: String,
                     password: String,
                     centre: String,
                     curs: String,
                     aula: String) {}

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