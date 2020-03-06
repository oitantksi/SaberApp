package omega.isaacbenito.saberapp.authentication.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import omega.isaacbenito.saberapp.authentication.AccountGlobals
import omega.isaacbenito.saberapp.authentication.ui.EnterDataError
import omega.isaacbenito.saberapp.authentication.ui.EnterDataState
import omega.isaacbenito.saberapp.authentication.ui.EnterDataSuccess
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
        when {
            //TODO User registration input validation rules
            password.length < AccountGlobals.MIN_PASSWORD_LENGTH -> _enterDataState.value =
                EnterDataError(1)
            else -> _enterDataState.value =
                EnterDataSuccess
        }
    }
}