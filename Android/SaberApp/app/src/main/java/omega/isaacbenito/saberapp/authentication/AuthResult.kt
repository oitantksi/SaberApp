package omega.isaacbenito.saberapp.authentication

/**
 * @author Isaac Benito
 *
 * Classe sellada que facilita la comunicació de l'estat del procés d'autorització
 * entre les diferents activitats i models del mòdul
 *
 */
sealed class AuthResult {
    object Success : AuthResult()
    data class Error(val error: Int) : AuthResult() {
        companion object {
            const val SERVER_UNREACHABLE_ERROR = 0
            const val NO_INTERNET_ACCESS = 1
            const val WRONG_CREDENTIALS_ERROR = 2
        }
    }
}
