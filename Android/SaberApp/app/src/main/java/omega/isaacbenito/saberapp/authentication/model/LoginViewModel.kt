package omega.isaacbenito.saberapp.authentication.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import omega.isaacbenito.saberapp.authentication.AuthenticationManager
import omega.isaacbenito.saberapp.authentication.ui.LoginState
import omega.isaacbenito.saberapp.authentication.ui.WrongCredentials
import javax.inject.Inject

class LoginViewModel @Inject constructor(): ViewModel() {

    @Inject lateinit var authenticationManager: AuthenticationManager

    private val _loginState = MutableLiveData<LoginState>()
    val loginState : LiveData<LoginState>
        get() = _loginState

    fun login(userMail: String, password: String) {
        if (!isValidEmail(userMail) or !isValidPassword(password)) {
            _loginState.value = WrongCredentials
        } else {
            authenticationManager.addUser(userMail, password)
        }
    }

    fun isValidEmail(userMail: String) : Boolean {
        return true
    }

    fun isValidPassword(password: String) : Boolean {
        return true
    }


}