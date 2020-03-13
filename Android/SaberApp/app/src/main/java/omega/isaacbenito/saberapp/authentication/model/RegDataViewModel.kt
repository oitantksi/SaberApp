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
import omega.isaacbenito.saberapp.authentication.AccountGlobals.Companion.isValidNameOrSurname
import omega.isaacbenito.saberapp.authentication.AccountGlobals.Companion.isValidNickname
import omega.isaacbenito.saberapp.authentication.AccountGlobals.Companion.isValidPassword
import omega.isaacbenito.saberapp.authentication.ui.EnterDataError
import omega.isaacbenito.saberapp.authentication.ui.EnterDataState
import omega.isaacbenito.saberapp.authentication.ui.EnterDataSuccess
import java.util.*
import javax.inject.Inject

/**
 * @author Isaac Benito
 *
 * Model de la vista de recollida de dades per al registre de l'usuari
 *
 */
class RegDataViewModel @Inject constructor() : ViewModel() {

    private val _tag = this.javaClass.name

    private val _enterDataState = MutableLiveData<EnterDataState>()
    val enterDetailsState: LiveData<EnterDataState>
        get() = _enterDataState

    /**
     * Valida les dades introduïdes per l'usuari, en cas de que alguna no sigui vàlida
     * registra la dada en qúestió en el motiu de l'error.
     *
     * @param user_name
     * @param user_surname
     * @param user_nickname
     * @param email
     * @param password
     */
    fun validateInput(
        user_name: String,
        user_surname: String,
        user_nickname: String,
        email: String,
        password: String,
        repeatPassword: String
    ) {
        if (!isValidEmail(email)) {
            _enterDataState.value = EnterDataError(EnterDataError.INVALID_EMAIL)
        } else if (!isValidPassword(password)) {
            _enterDataState.value = EnterDataError(EnterDataError.INVALID_PASSWORD)
        } else if (repeatPassword != password) {
            _enterDataState.value = EnterDataError(EnterDataError.INVALID_PASSWORD_REPEAT)
        } else if (!isValidNameOrSurname(user_name.toLowerCase(Locale.getDefault()))) {
            _enterDataState.value = EnterDataError(EnterDataError.INVALID_NAME)
        } else if (!isValidNameOrSurname(user_surname.toLowerCase(Locale.getDefault()))) {
            _enterDataState.value = EnterDataError(EnterDataError.INVALID_SURNAME)
        } else if (!isValidNickname(user_nickname)) {
            _enterDataState.value = EnterDataError(EnterDataError.INVALID_NICKNAME)
        } else {
            _enterDataState.value = EnterDataSuccess
        }
    }

    /**
     * Calcula la força de la contrassenya introduïda per l'usuari en base a la longitud,
     * la quantitat de minúscules, majúscules, dígits i simbols.
     *
     * Es consideren característiques positives (sumen punts):
     *  - La longitud (longitud * 4)
     *  - La quantitat de minúscules (quantitat * 2)
     *  - La quantitat de majúscules (quantitat * 2)
     *  - La quantitat de dígits (quantitat * 4)
     *  - La quantitat de simbols (quantitat * 6)
     *
     *  Es consideren característique negatives (resten punts):
     *   - La quantitat de caràcters repetit al llarg de la contrassenya (quantitat)
     *   - La quantitat de minúscules repetides al llarg de la contrassenya (quantitat * 2)
     *   - La quantitat de majúscules repetides al llarg de la contrassenya (quantitat * 2)
     *   - La quantitat de dígits repetits al llarg de la contrassenya (quantitat * 2)
     *   - Les cadenes de dos o més minúscules consecutives (quantitat * 3)
     *   - Les cadenes de dos o més majúscules consecutives (quantitat * 3)
     *   - Les cadenes de dos o més dígits consecutius (quantitat * 3)
     *
     * Aquest mètode no s'usa actualment però està previst implementar-ne una
     * visualització del resultat més endavant.
     *
     * @param password
     * @return Valor de 0 a 100 representant la força de la contrassenya de l'usuari.
     */
    fun calculatePasswordStrength(password: String): Int {
        val length = password.length * 4
        val upper = (password.length - Regex("[A-Z]").findAll(password).count()) * 2
        val lower = (password.length - Regex("[a-z]").findAll(password).count()) * 2
        val numbers = Regex("[\\d]").findAll(password).count() * 4
        val symbols = Regex("[ !#$%&()*+,\\-.:;<>?@\\[\\]{}^_|]").findAll(password).count() * 6
        val repeatChar = Regex("(.)\\1+").findAll(password.toLowerCase(Locale.getDefault())).count()
        val repeatLower = Regex("([a-z])\\1+").findAll(password).count() * 2
        val repeatUpper = Regex("([A-Z])\\1+").findAll(password).count() * 2
        val repeatNumber = Regex("(\\d)\\1+").findAll(password).count() * 2
        val lowerSequence = Regex("([a-z])\\1{2,}").findAll(password).count() * 3
        val upperSequence = Regex("([A-Z])\\1{2,}").findAll(password).count() * 3
        val digitSequence = Regex("(\\d)\\1{2,}").findAll(password).count() * 3

        val additions = length + upper + lower + numbers + symbols
        val deductions = (repeatChar + repeatLower + repeatUpper + repeatNumber
                + lowerSequence + upperSequence + digitSequence)

        val score = additions - deductions

        return if (score > 100) 100 else score
    }
}