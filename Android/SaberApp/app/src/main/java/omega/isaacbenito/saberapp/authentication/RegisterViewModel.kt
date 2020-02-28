package omega.isaacbenito.saberapp.authentication

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegisterViewModel : ViewModel() {

    val TAG = this.javaClass.name.toString()

    private val _alreadyMember = MutableLiveData<Boolean>()
    val alreadyMember : LiveData<Boolean>
        get() = _alreadyMember

    init {
        _alreadyMember.value = false
    }

    fun alreadyMember() {
        _alreadyMember.value = _alreadyMember.value != true
    }
}