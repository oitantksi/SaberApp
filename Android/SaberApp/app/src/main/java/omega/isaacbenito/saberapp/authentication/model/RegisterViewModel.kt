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
import omega.isaacbenito.saberapp.authentication.AuthResult
import omega.isaacbenito.saberapp.authentication.di.AuthScope
import omega.isaacbenito.saberapp.authentication.impl.AuthenticationManagerImpl
import timber.log.Timber
import javax.inject.Inject

/**
 * Model de la vista de l'activitat d'autenticació.
 *
 * Totes les activitats i fragments del mòdul d'autenticació comparteixen una única
 * instància d'aquesta classe.
 *
 * @author Isaac Benito1
 */
@AuthScope
class RegisterViewModel @Inject constructor() : ViewModel() {

    @Inject lateinit var authenticationManager: AuthenticationManagerImpl

    private lateinit var userName: String
    private lateinit var userSurname: String
    private lateinit var userNickname: String
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var centre: String

    /**
     * Desa les dades introduïdes per l'usuari en el fragment de recollida de dades
     * per al registre.
     *
     * @param user_name
     * @param user_surname
     * @param user_nickname
     * @param email
     * @param password
     */
    fun updateUserData(
        user_name: String,
        user_surname: String,
        user_nickname: String,
        email: String,
        password: String
    ) {
        this.userName = user_name
        this.userSurname = user_surname
        this.userNickname = user_nickname
        this.email = email
        this.password = password
    }

    /**
     * Desa les dades introduïdes per l'usuari en el fragment de selecció de centre
     * i crida al mètode registerUser.
     *
     * @param centre
     */
    fun updateCentreData(centre: String) {
        this.centre = centre

        registerUser()
    }

    private val _registrationStatus = MutableLiveData<AuthResult>()
    val registrationStatus : LiveData<AuthResult>
        get() = _registrationStatus

    /**
     * Crida al gestor d'autenticació per a iniciar el procés de registre i li
     * facilita les dades obtingudes.
     *
     * Obte un valor d'estat de l'autenticació que desa per a que pugui ser observat.
     */
    private fun registerUser() {
        Timber.d(userName, userSurname, userNickname, email, password, centre)
        _registrationStatus.value = authenticationManager.register(
            userName, userSurname, userNickname, email, password, centre).value
    }
}