package omega.isaacbenito.saberapp.authentication

import android.util.Patterns

class AccountGlobals {
    companion object {
        // Password restrictions
        val MIN_PASSWORD_LENGTH = 5
        val PASSWORD_MUST_HAVE_UPPER = false
        val PASSWORD_MUST_HAVE_DIGIT = false
        val PASSWORD_MUST_HAVE_SYMBOL = false
        val PASSWORD_RESTRICTIONS = "^(?=.*[a-z])" +
                "${if (PASSWORD_MUST_HAVE_UPPER) "(?=.*[A-Z])" else ""}" +
                "${if (PASSWORD_MUST_HAVE_DIGIT) "(?=.*\\d)" else ""}" +
                "${if (PASSWORD_MUST_HAVE_SYMBOL) "(?=.*[ !#\$%&()*+,\\-.:;<>?@\\[\\]{}^_|])" else ""}" +
                "[A-Za-z\\d]{4,}\$"

        // Account nickname restrictions
        val MIN_NICNAME_LENGTH = 4

        /**
         * Comprova si el email proporcionat per l'usuari és una cadena de text amb format email correcta
         *
         * @param userMail email proporcionat per l'usuari
         * @return Boolean
         */
        fun isValidEmail(userMail: String) : Boolean {
            return if (userMail.contains('@')) {
                Patterns.EMAIL_ADDRESS.matcher(userMail).matches()
            } else {
                userMail.isNotBlank()
            }
        }

        /**
         * Comprova si la contrassenya proporcionada per l'usuari compleix les característiques necessàries
         *
         * @param password
         * @return
         */
        fun isValidPassword(password: String) : Boolean {
            return Regex(AccountGlobals.PASSWORD_RESTRICTIONS).matches(password)
        }

    }


}
