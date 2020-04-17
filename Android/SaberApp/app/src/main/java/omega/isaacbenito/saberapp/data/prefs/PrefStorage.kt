package omega.isaacbenito.saberapp.data.prefs

/**
 * Interficie que defineix els metodes per a desar i obtindre objectes clau -> valor
 * de l'emmagatzematge del dispositiu
 *
 * @author Isaac Benito
 **/
interface PrefStorage {

    fun setCurrentUserName(userName: String)

    fun getCurrentUserName() : String

    fun setCurrentUserId(userId: Long)

    fun getCurrentUserId() : Long
}