package omega.isaacbenito.saberapp.data.local

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import omega.isaacbenito.saberapp.data.AppLocalDataSource
import omega.isaacbenito.saberapp.data.Result
import omega.isaacbenito.saberapp.data.Result.Error
import omega.isaacbenito.saberapp.data.Result.Success
import omega.isaacbenito.saberapp.data.entities.Centre
import omega.isaacbenito.saberapp.data.entities.User
import omega.isaacbenito.saberapp.data.local.database.dao.CentreDao
import omega.isaacbenito.saberapp.data.local.database.dao.UserDao
import timber.log.Timber

class SaberAppLocalDataSource internal constructor(
    private val userDao: UserDao,
    private val centreDao: CentreDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : AppLocalDataSource {


    override suspend fun getAllCentres(): Result<LiveData<List<Centre>>> =
        withContext(ioDispatcher) {
            return@withContext try {
                Success(centreDao.getAllCentres())
            } catch (e: Exception) {
                Error(e)
            }
        }

    override suspend fun updateCentres(centres: List<Centre>): Result<Unit> =
        withContext(ioDispatcher) {
            return@withContext try {
                Success(centreDao.save(centres))
            } catch (e: Exception) {
                Error(e)
            }
        }

    override suspend fun getUser(email: String): Result<LiveData<User>> {
        return try {
            val user = userDao.get(email)
            Timber.d("Recuperat l'usuari: ${user.value}")
            val users = userDao.getAll()
            Timber.d("Recuperats els usuaris: ${users.value}")
            Success(user)
        } catch (e: Exception) {
            Error(e)
        }
    }

    override suspend fun saveUser(user: User): Result<Unit> = withContext(ioDispatcher) {
        return@withContext try {
            Success(userDao.save(user))
        } catch (e: Exception) {
            Error(e)
        }
    }
}