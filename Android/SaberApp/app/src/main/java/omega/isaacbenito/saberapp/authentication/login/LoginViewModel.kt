package omega.isaacbenito.saberapp.authentication.login

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.*
import omega.isaacbenito.saberapp.authentication.AuthenticationManager
import omega.isaacbenito.saberapp.authentication.ServerAuthenticate
import javax.inject.Inject

class LoginViewModel @Inject constructor() : ViewModel() {

    val TAG = this.javaClass.name

    @Inject lateinit var authenticationManager: AuthenticationManager

    private val _newUser = MutableLiveData<Boolean>(false)
    val newUser : LiveData<Boolean>
        get() = _newUser

    fun newUser() {
        _newUser.value = _newUser.value != true
        Log.d(TAG, "new user")
    }

    private val _loginState = MutableLiveData<LoginState>()
    val loginState : LiveData<LoginState>
        get() = _loginState

    fun login(userMail: String, password: String) : LiveData<LoginState> {
        return authenticationManager.loginUser(userMail, password)

    }




}