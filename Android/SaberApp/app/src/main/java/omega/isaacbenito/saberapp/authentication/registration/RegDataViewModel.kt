package omega.isaacbenito.saberapp.authentication.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import omega.isaacbenito.saberapp.R

private const val MIN_PASSWORD_LENGTH = 5

class RegDataViewModel : ViewModel() {

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
        when {
            //TODO User registration input validation rules
            password.length < MIN_PASSWORD_LENGTH -> _enterDataState.value = EnterDataError(1)
            else -> _enterDataState.value = EnterDataSuccess
        }
    }
}