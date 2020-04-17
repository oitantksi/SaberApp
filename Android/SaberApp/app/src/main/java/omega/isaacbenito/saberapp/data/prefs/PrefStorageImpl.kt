package omega.isaacbenito.saberapp.data.prefs

import android.content.Context
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementació dels metodes per a desar i obtindre objectes clau -> valor
 * de l'emmagatzematge del dispositiu
 *
 * @author Isaac Benito
 **/
@Singleton
class PrefStorageImpl @Inject constructor(context: Context) : PrefStorage {

    companion object {
        const val SHARED_PREF_STORAGE = "omega.SaberApp.SharedStorage"

        const val ACCOUNT_NAME = "ACCOUNT_NAME"
        const val ACCOUNT_ID = "ACCOUNT_ID"
    }

    private val prefStorage = context.getSharedPreferences(SHARED_PREF_STORAGE, Context.MODE_PRIVATE)

    override fun setCurrentUserName(userName: String) {
        with(prefStorage.edit()) {
            putString(ACCOUNT_NAME, userName)
            apply()
        }
    }

    override fun getCurrentUserName(): String {
        return prefStorage.getString(ACCOUNT_NAME, "") ?: throw NullPointerException()
    }

    override fun setCurrentUserId(userId: Long) {
        with(prefStorage.edit()) {
            putLong(ACCOUNT_ID, userId)
            apply()
        }
    }

    override fun getCurrentUserId(): Long {
        return prefStorage.getLong(ACCOUNT_ID, 0) ?: throw NullPointerException()
    }
}