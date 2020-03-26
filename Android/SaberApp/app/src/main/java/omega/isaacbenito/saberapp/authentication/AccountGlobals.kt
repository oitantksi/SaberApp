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

package omega.isaacbenito.saberapp.authentication

import java.util.*

/**
 *  Classe estàtica que proporciona les característiques globals del tipus de compte de l'aplicació.
 *
 *  Estableix les característiques necessàries de cada tipus de dada requerida
 *  per a crear un compte de l'aplicació i proporciona mètodes estàtics amb els que poder
 *  verificar les diferents dades.
 */
class AccountGlobals {
    companion object {

        const val ACCOUNT_TYPE = "omega.saberapp"

        //Email validation pattern
        private const val EMAIL_VALIDATION_PATTERN =
            "^[a-zA-Z\\d\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@[a-zA-Z\\d][a-zA-Z\\d\\-]{1,64}" +
                    "(\\.[a-zA-Z\\d][a-zA-Z\\d\\-]{1,25})\$"

        // Password restrictions
        private const val PASSWORD_MUST_HAVE_LOWER = true
        private const val PASSWORD_MUST_HAVE_UPPER = true
        private const val PASSWORD_MUST_HAVE_DIGIT = true
        private const val PASSWORD_MUST_HAVE_SYMBOL = true
        private const val ALLOWED_PASSWORD_SYMBOLS = " !#\$%&()*+,\\-.:;<>?@\\[\\]{}^_|"
        private const val MIN_PASSWORD_LENGTH = 8
        private const val MAX_PASSWORD_LENGTH = ""
        private val PASSWORD_RESTRICTIONS =
            (if (PASSWORD_MUST_HAVE_LOWER) "^(?=.*[a-z])" else "") +
                    (if (PASSWORD_MUST_HAVE_UPPER) "(?=.*[A-Z])" else "") +
                    (if (PASSWORD_MUST_HAVE_DIGIT) "(?=.*\\d)" else "") +
                    (if (PASSWORD_MUST_HAVE_SYMBOL) "(?=.*[$ALLOWED_PASSWORD_SYMBOLS])" else "") +
                    "[A-Za-z\\d$ALLOWED_PASSWORD_SYMBOLS]" +
                    "{$MIN_PASSWORD_LENGTH,$MAX_PASSWORD_LENGTH}\$"

        // Account nickname restrictions
        private const val NICKNAME_ALLOWED_UPPER = true
        private const val NICKNAME_ALLOWED_DIGIT = true
        private const val ALLOWED_NICKNAME_SPECIAL_CHARACTERS = "\\-\\._"
        private const val MIN_NICNAME_LENGTH = 3
        private const val MAX_NICNAME_LENGTH = 15
        private val NICKNAME_RESTRICTIONS =
            "^[a-z" +
                    (if (NICKNAME_ALLOWED_UPPER) "A-Z" else "") +
                    (if (NICKNAME_ALLOWED_DIGIT) "\\d" else "") +
                    "$ALLOWED_NICKNAME_SPECIAL_CHARACTERS]" +
                    "{$MIN_NICNAME_LENGTH,$MAX_NICNAME_LENGTH}\$"

        // Name and surname restrictions
        private const val ALLOWED_LOWER_SPECIAL_CHARACTERS = "àáèéíïòóúü ."
        private const val MIN_NAME_LENGTH = 2
        private const val MAX_NAME_LEGTH = 50
        private const val NAME_SURNAME_RESTRICTIONS =
            "^[a-z$ALLOWED_LOWER_SPECIAL_CHARACTERS]" +
                    "{$MIN_NAME_LENGTH,$MAX_NAME_LEGTH}\$"

        /**
         * Comprova si el email proporcionat per l'usuari és una cadena de text amb format email correcta
         *
         * @param userMail email proporcionat per l'usuari
         * @return true si és un email vàlid, false en altre cas.
         */
        fun isValidEmail(userMail: String): Boolean {
            return checkStringValidity(userMail, EMAIL_VALIDATION_PATTERN)
        }

        /**
         * Comprova si la contrassenya proporcionada per l'usuari compleix les característiques necessàries
         *
         * @param password  password proporcionat per l'usuari
         * @return true si és un password vàlid, false en altre cas.
         */
        fun isValidPassword(password: String): Boolean {
            return checkStringValidity(password, PASSWORD_RESTRICTIONS)
        }

        /**
         * Comprova si el nom o els cognoms proporcionats per l'usuari compleixen
         * les característiques necessàries
         *
         * @param name  nom o cognoms proporcionats per l'usuari
         * @return true si és un nom o cognoms vàlid, false en altre cas.
         */
        fun isValidNameOrSurname(name: String): Boolean {
            return checkStringValidity(name.toLowerCase(), NAME_SURNAME_RESTRICTIONS)
        }

        /**
         * Comprova si el àlies proporcionat per l'usuari compleix
         * les característiques necessàries
         *
         * @param nickname  àlies proporcionats per l'usuari
         * @return true si és un àlies vàlid, false en altre cas.
         */
        fun isValidNickname(nickname: String): Boolean {
            return checkStringValidity(nickname, NICKNAME_RESTRICTIONS)
        }

        /**
         * Comprova que una cadena de text no estigui buida i s'ajusti a les restriccions
         *
         * @param string cadena de text a verificar
         * @param restrictions regular expression amb les que verificar
         * @return true si no està buida i s'ajusta a les restriccions, false en altre cas.
         */
        private fun checkStringValidity(string: String, restrictions: String): Boolean {
            return if (string.isNotBlank()) {
                Regex(restrictions).matches(string)
            } else {
                false
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
            val repeatChar =
                Regex("(.)\\1+").findAll(password.toLowerCase(Locale.getDefault())).count()
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
}
