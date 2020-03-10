package omega.isaacbenito.saberapp.authentication.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import omega.isaacbenito.saberapp.authentication.AuthenticationManager
import omega.isaacbenito.saberapp.authentication.ui.AuthState
import omega.isaacbenito.saberapp.di.ActivityScope
import javax.inject.Inject

@ActivityScope
class RegisterViewModel @Inject constructor() : ViewModel() {

    @Inject lateinit var authenticationManager: AuthenticationManager

    private lateinit var user_name: String
    private lateinit var user_surname: String
    private lateinit var user_nickname: String
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var centre: String
    private lateinit var curs: String
    private lateinit var aula: String

    fun updateUserData(
        user_name: String,
        user_surname: String,
        user_nickname: String,
        email: String,
        password: String
    ) {
        this.user_name = user_name
        this.user_surname = user_surname
        this.user_nickname = user_nickname
        this.email = email
        this.password = password
    }

    fun updateCentreData(centre: String) {
        this.centre = centre

        registerUser()
    }

    private val _registrationStatus = MutableLiveData<AuthState>()
    val registrationStatus : LiveData<AuthState>
        get() = _registrationStatus

    fun registerUser() {
        _registrationStatus.value = authenticationManager.registerUser(
            user_name, user_surname, user_nickname, email, password, centre).value
    }




}