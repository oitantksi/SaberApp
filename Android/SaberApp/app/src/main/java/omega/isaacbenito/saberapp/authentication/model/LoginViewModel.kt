package omega.isaacbenito.saberapp.authentication.model

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import omega.isaacbenito.saberapp.authentication.AccountGlobals
import omega.isaacbenito.saberapp.authentication.AccountGlobals.Companion.isValidEmail
import omega.isaacbenito.saberapp.authentication.AccountGlobals.Companion.isValidPassword
import omega.isaacbenito.saberapp.authentication.AuthenticationManager
import omega.isaacbenito.saberapp.authentication.ui.AuthError
import omega.isaacbenito.saberapp.authentication.ui.AuthState
import javax.inject.Inject
import kotlin.math.log

class LoginViewModel @Inject constructor(): ViewModel() {

    @Inject lateinit var authenticationManager: AuthenticationManager

    private var _loginState = MutableLiveData<AuthState>()
    val loginState : LiveData<AuthState>
        get() = _loginState

    fun login(userMail: String, password: String) {
        _loginState.value = authenticationManager.login(userMail, password).value
    }
}