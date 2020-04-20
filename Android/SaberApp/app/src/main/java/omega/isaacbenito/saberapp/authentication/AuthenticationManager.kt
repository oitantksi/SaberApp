package omega.isaacbenito.saberapp.authentication

import androidx.lifecycle.LiveData

/**
 * Gestor de l'autenticació d'usuari
 *
 * @author Isaac Benito
 **/
interface AuthenticationManager {

    fun login(userAccountIdentifier: String, password: String) : LiveData<AuthResult>

    fun logout()

    fun logout(accountName: String)

    fun register(user_name: String,
                 user_surname: String,
                 user_nickname: String,
                 email: String,
                 password: String,
                 centre: String
    ) : LiveData<AuthResult>

    fun unregister() : LiveData<AuthResult>

    fun unregister(accountName: String) : LiveData<AuthResult>

    fun userIsLoggedIn() : Boolean

    fun getAuthToken() : String

    fun getUserAccountIdentifier(): String

    fun updateUserAccountIdentifier(accountName: String) : LiveData<AuthResult>

    fun updateUserPassword(oldPassword: String, newPassword: String) : LiveData<AuthResult>
}

