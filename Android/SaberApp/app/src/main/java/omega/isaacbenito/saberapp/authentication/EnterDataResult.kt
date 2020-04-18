package omega.isaacbenito.saberapp.authentication

/**
 * @author Isaac Benito
 *
 * Classe sellada que facilita la comunicació de la validesa de les dades introduïdes
 * entre les diferents activitats i models del mòdul
 *
 */
sealed class EnterDataResult {
    object Success : EnterDataResult()
    data class Error(val errorCode: Int) : EnterDataResult() {
        companion object {
            const val WRONG_CREDENTIALS = 0
            const val INVALID_EMAIL = 1
            const val INVALID_PASSWORD = 2
            const val INVALID_EMAIL_AND_PASSWORD = 3
            const val INVALID_PASSWORD_REPEAT = 4
            const val INVALID_NAME = 5
            const val INVALID_SURNAME = 6
            const val INVALID_NICKNAME = 7
        }
    }
}


