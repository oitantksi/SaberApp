package omega.isaacbenito.saberapp.authentication

import android.util.Log
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

class AuthenticationManager ()  {

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
                        _loginState.value = LoginError
                    }
                }

                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    _loginState.value = LoginError
                }
            }
        )
        return loginState
    }

    fun setUserToken(authToken: String) {}

    fun getAuthToken(): String {return ""}

    fun registerUser() {}

    fun logoutUser() {}

    fun unregisterUser() {}


}