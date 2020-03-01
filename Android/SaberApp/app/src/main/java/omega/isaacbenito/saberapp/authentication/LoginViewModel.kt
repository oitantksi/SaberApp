package omega.isaacbenito.saberapp.authentication

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

    val TAG = this.javaClass.name

    val authenticator = AuthenticatorActivity()

    private val _newUser = MutableLiveData<Boolean>()
    val newUser : LiveData<Boolean>
        get() = _newUser

    init {
        Log.i(TAG, "LoginViewModel Created!")
        _newUser.value = false
    }

    fun login(userMail: String, password: String) {
        authenticator.login(userMail, password)
    }

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

    fun newUser() {
        _newUser.value = _newUser.value != true
        Log.d(TAG, "new user")
    }
}