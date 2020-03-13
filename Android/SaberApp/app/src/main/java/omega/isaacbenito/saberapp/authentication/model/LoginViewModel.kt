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

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import omega.isaacbenito.saberapp.authentication.AccountGlobals.Companion.isValidEmail
import omega.isaacbenito.saberapp.authentication.AccountGlobals.Companion.isValidPassword
import omega.isaacbenito.saberapp.authentication.AuthenticationManager
import omega.isaacbenito.saberapp.authentication.ui.AuthState
import omega.isaacbenito.saberapp.authentication.ui.EnterDataError
import omega.isaacbenito.saberapp.authentication.ui.EnterDataState
import omega.isaacbenito.saberapp.authentication.ui.EnterDataSuccess
import javax.inject.Inject

/**
 * @author Isaac Benito
 *
 * Model de la vista de login d'usuari
 *
 */
class LoginViewModel @Inject constructor() : ViewModel() {

    @Inject
    lateinit var authenticationManager: AuthenticationManager

    private var _loginState = MutableLiveData<AuthState>()
    val loginState: LiveData<AuthState>
        get() = _loginState

    private val _enterDataState = MutableLiveData<EnterDataState>()
    val enterDataState: LiveData<EnterDataState>
        get() = _enterDataState

    /**
     * Crida al gestor d'autenticació per a que realitzi el login amb les credencials
     * facilitades per l'usuari.
     *
     * @param userMail
     * @param password
     */
    fun login(userMail: String, password: String) {
        _loginState.value = authenticationManager.login(userMail, password).value
    }

    /**
     * Valida les dades introduïdes per l'usuari segons els principis que aquestes han de complir
     * per a poder ser credencials d'inici de sessió de l'aplicació
     *
     * @param userMail
     * @param password
     */
    fun validateInput(userMail: String, password: String) {
        if (!isValidEmail(userMail)) {
            _enterDataState.value = EnterDataError(EnterDataError.INVALID_EMAIL)
        } else if (!isValidPassword(password)) {
            _enterDataState.value = EnterDataError(EnterDataError.INVALID_EMAIL)
        } else {
            _enterDataState.value = EnterDataSuccess
        }
    }
}