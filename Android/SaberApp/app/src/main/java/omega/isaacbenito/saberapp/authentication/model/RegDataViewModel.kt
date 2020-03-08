package omega.isaacbenito.saberapp.authentication.model

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import omega.isaacbenito.saberapp.authentication.AccountGlobals
import omega.isaacbenito.saberapp.authentication.AccountGlobals.Companion.isValidEmail
import omega.isaacbenito.saberapp.authentication.AccountGlobals.Companion.isValidPassword
import omega.isaacbenito.saberapp.authentication.ui.*
import javax.inject.Inject

private const val MIN_PASSWORD_LENGTH = 5

class RegDataViewModel @Inject constructor() : ViewModel() {

    val TAG = this.javaClass.name.toString()

    private val _alreadyMember = MutableLiveData<Boolean>()
    val alreadyMember : LiveData<Boolean>
        get() = _alreadyMember

    fun alreadyMember() {
        _alreadyMember.value = _alreadyMember.value != true
    }

    private val _enterDataState = MutableLiveData<EnterDataState>()
    val enterDetailsState: LiveData<EnterDataState>
        get() = _enterDataState

    init {
        _alreadyMember.value = false
    }

    fun validateInput(
        user_name: String,
        user_surname: String,
        user_nickname: String,
        email: String,
        password: String
    ) {
        if (!isValidEmail(email)) {
            _enterDataState.value = InvalidEmail
        } else if (!isValidPassword(password)) {
            _enterDataState.value = InvalidPassword
        } else {
            _enterDataState.value = EnterDataSuccess
        }
    }

    fun calculatePasswordStrength(password: String) : Int {
        val length = password.length * 4
        val upper = (password.length - Regex("[A-Z]").findAll(password).count()) * 2
        val lower = (password.length - Regex("[a-z]").findAll(password).count()) * 2
        val numbers = Regex("[\\d]").findAll(password).count() * 4
        val symbols = Regex("[ !#$%&()*+,\\-.:;<>?@\\[\\]{}^_|]").findAll(password).count() * 6
        val repeatChar = Regex("(.)\\1+").findAll(password.toLowerCase()).count()
        val repeatLower = Regex("([a-z])\\1+").findAll(password.toLowerCase()).count() * 2
        val repeatUpper = Regex("([A-Z])\\1+").findAll(password.toLowerCase()).count() * 2
        val repeatNumber = Regex("(\\d)\\1+").findAll(password.toLowerCase()).count() * 2
        val lowerSequence = Regex("([a-z])\\1{2,}").findAll(password.toLowerCase()).count() * 3
        val upperSequence = Regex("([A-Z])\\1{2,}").findAll(password.toLowerCase()).count() * 3
        val digitSequence = Regex("(\\d)\\1{2,}").findAll(password.toLowerCase()).count() * 3

        val additions = length + upper + lower + numbers + symbols
        val deductions = (repeatChar + repeatLower + repeatUpper + repeatNumber
                + lowerSequence + upperSequence + digitSequence)

        val score = additions - deductions

        return if (score > 100) 100 else score
    }
}