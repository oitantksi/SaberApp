package omega.isaacbenito.saberapp.authentication

/**
 * Respresenta l'estat d'autenticació del compte actual
 *
 * @author Isaac Benito
 **/
sealed class AuthState {
    object LoggedIn: AuthState()
    object LoggedOut: AuthState()
}