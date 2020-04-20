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
import omega.isaacbenito.saberapp.authentication.AccountGlobals.Companion.isValidPassword
import omega.isaacbenito.saberapp.authentication.AuthResult
import omega.isaacbenito.saberapp.authentication.EnterDataResult
import omega.isaacbenito.saberapp.authentication.impl.AuthenticationManagerImpl
import javax.inject.Inject
import omega.isaacbenito.saberapp.authentication.EnterDataResult.Error as EnterDataError
import omega.isaacbenito.saberapp.authentication.EnterDataResult.Success as EnterDataSuccess

/**
 * Model de la vista d'inici de sessió de l'aplicació
 *
 * @author Isaac Benito
 */
class LoginViewModel @Inject constructor() : ViewModel() {

    @Inject
    lateinit var authenticationManager: AuthenticationManagerImpl

    private var _loginState = MutableLiveData<AuthResult>()
    val loginResult: LiveData<AuthResult>
        get() = _loginState

    private val _enterDataState = MutableLiveData<EnterDataResult>()
    val enterDataResult: LiveData<EnterDataResult>
        get() = _enterDataState

    val userMail = ObservableField<String>()
    val userPassword = ObservableField<String>()

    /**
     * Crida al gestor d'autenticació per a que realitzi el login amb les credencials
     * facilitades per l'usuari.
     *
     */
    fun login() {
        _loginState.value = authenticationManager.login(
            userMail.get()!!, userPassword.get()!!
        ).value
    }

    /**
     * Valida les dades introduïdes per l'usuari segons els principis que aquestes han de complir
     * per a poder ser credencials d'inici de sessió de l'aplicació
     *
     */
    fun validateInput() {
        if (userMail.get() == null || userPassword.get() == null) {
            _enterDataState.value = EnterDataError(EnterDataError.INVALID_EMAIL_AND_PASSWORD)
        } else {
            val mail = userMail.get()!!
            val password = userPassword.get()!!

            if (mail.isEmpty() && password.isEmpty()) {
                _enterDataState.value = EnterDataError(EnterDataError.INVALID_EMAIL_AND_PASSWORD)
            } else if (mail.isEmpty()) {
                _enterDataState.value = EnterDataError(EnterDataError.INVALID_EMAIL)
            } else if (password.isEmpty()) {
                _enterDataState.value = EnterDataError(EnterDataError.INVALID_PASSWORD)
            } else if (!isValidEmail(mail) || !isValidPassword(password)) {
                _enterDataState.value = EnterDataError(EnterDataError.WRONG_CREDENTIALS)
            } else {
                _enterDataState.value = EnterDataSuccess
            }

        }
    }

    private val _newUser = MutableLiveData<Boolean>()
    val newUser: LiveData<Boolean>
        get() = _newUser

    /**
     * Estableix que es tracta d'un usuari nou.
     */
    fun isNewUser() {
        _newUser.value = _newUser.value != true
    }
}