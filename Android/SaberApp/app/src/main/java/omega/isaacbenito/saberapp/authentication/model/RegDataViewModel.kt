/*
 * This file is part of SaberApp.
 *
 *     SaberApp is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     SaberApp is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with SaberApp.  If not, see <https://www.gnu.org/licenses/>.
 */

package omega.isaacbenito.saberapp.authentication.model

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import omega.isaacbenito.saberapp.authentication.AccountGlobals.Companion.isValidEmail
import omega.isaacbenito.saberapp.authentication.AccountGlobals.Companion.isValidNameOrSurname
import omega.isaacbenito.saberapp.authentication.AccountGlobals.Companion.isValidNickname
import omega.isaacbenito.saberapp.authentication.AccountGlobals.Companion.isValidPassword
import omega.isaacbenito.saberapp.authentication.EnterDataResult
import javax.inject.Inject
import omega.isaacbenito.saberapp.authentication.EnterDataResult.Error as EnterDataError
import omega.isaacbenito.saberapp.authentication.EnterDataResult.Success as EnterDataSuccess

/**
 * Model de la vista de recollida de dades per al registre de l'usuari
 *
 * @author Isaac Benito
 */
class RegDataViewModel @Inject constructor() : ViewModel() {

    val userName = ObservableField<String>()
    val userSurname = ObservableField<String>()
    val userNickname = ObservableField<String>()
    val userMail = ObservableField<String>()
    val userPassword = ObservableField<String>()
    val userPasswordRepeat = ObservableField<String>()

    private val _enterDataState = MutableLiveData<EnterDataResult>()
    val enterDetailsResult: LiveData<EnterDataResult>
        get() = _enterDataState


    /**
     * Valida les dades introduïdes per l'usuari, en cas de que alguna no sigui vàlida
     * registra la dada en qúestió en el motiu de l'error.
     */
    fun validateInput() {
        if (userMail.get() == null
            || userMail.get()!!.isEmpty()
            || !isValidEmail(userMail.get()!!)
        ) {
            _enterDataState.value = EnterDataError(EnterDataError.INVALID_EMAIL)
        } else if (userPassword.get() == null
            || userPassword.get()!!.isEmpty()
            || !isValidPassword(userPassword.get()!!)
        ) {
            _enterDataState.value = EnterDataError(EnterDataError.INVALID_PASSWORD)
        } else if (userPasswordRepeat.get() == null
            || userPassword.get() == null
            || userPasswordRepeat.get()!!.isEmpty()
            || userPassword.get()!! != userPasswordRepeat.get()!!
        ) {
            _enterDataState.value = EnterDataError(EnterDataError.INVALID_PASSWORD_REPEAT)
        } else if (userName.get() == null
            || userName.get()!!.isEmpty()
            || !isValidNameOrSurname(userName.get()!!)
        ) {
            _enterDataState.value = EnterDataError(EnterDataError.INVALID_NAME)
        } else if (userSurname.get() == null
            || userSurname.get()!!.isEmpty()
            || !isValidNameOrSurname(userSurname.get()!!)
        ) {
            _enterDataState.value = EnterDataError(EnterDataError.INVALID_SURNAME)
        } else if (userNickname.get() == null
            || userNickname.get()!!.isEmpty()
            || !isValidNickname(userNickname.get()!!)
        ) {
            _enterDataState.value = EnterDataError(EnterDataError.INVALID_NICKNAME)
        } else {
            _enterDataState.value = EnterDataSuccess
        }
    }

    private val _alreadyMember = MutableLiveData<Boolean>()
    val alreadyMember: LiveData<Boolean>
        get() = _alreadyMember

    /**
     * Estableix que es tracta d'un usuari ja registrat.
     */
    fun alreadyMember() {
        _alreadyMember.value = _alreadyMember.value != true
    }
}