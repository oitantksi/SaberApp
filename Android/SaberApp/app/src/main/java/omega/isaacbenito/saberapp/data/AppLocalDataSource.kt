package omega.isaacbenito.saberapp.data

import androidx.lifecycle.LiveData
import omega.isaacbenito.saberapp.data.entities.Centre
import omega.isaacbenito.saberapp.data.entities.User

interface AppLocalDataSource {

    suspend fun getAllCentres(): Result<LiveData<List<Centre>>>

    suspend fun updateCentres(centres: List<Centre>): Result<Unit>

    suspend fun getUser(email: String): Result<LiveData<User>>

    suspend fun saveUser(user: User): Result<Unit>
}