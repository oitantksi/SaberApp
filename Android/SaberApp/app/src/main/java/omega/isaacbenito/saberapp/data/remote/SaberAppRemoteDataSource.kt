package omega.isaacbenito.saberapp.data.remote

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import omega.isaacbenito.saberapp.data.AppRemoteDataSource
import omega.isaacbenito.saberapp.data.Result
import omega.isaacbenito.saberapp.data.Result.Error
import omega.isaacbenito.saberapp.data.Result.Success
import omega.isaacbenito.saberapp.data.entities.Centre
import omega.isaacbenito.saberapp.data.entities.User
import omega.isaacbenito.saberapp.data.remote.server.SaberAppServer
import retrofit2.HttpException
import java.net.ConnectException

/**
 * Implementació de l'interficie [AppRemoteDataSource]
 *
 * @author Isaac Benito
 * @property server
 * @property ioDispatcher
 */
class SaberAppRemoteDataSource(
    private val server: SaberAppServer,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : AppRemoteDataSource {

    override suspend fun getAllCentres(): Result<List<Centre>> = withContext(ioDispatcher) {
        return@withContext try {
            val response = server.apiService.getCentres()

            if (response.isSuccessful && response.body() != null) {
                Success(response.body()!!)
            } else {
                Error(Exception(response.message()))
            }
        } catch (e: ConnectException) {
            Error(e)
        }
    }

    override suspend fun getUser(email: String): Result<User> = withContext(ioDispatcher) {
        return@withContext try {
            val response = server.apiService.getUser(email)
            if (response.isSuccessful && response.body() != null) {
                Success(response.body()!!)
            } else {
                Error(Exception(response.message()))
            }
        } catch (exception: ConnectException) {
            Error(exception)
        }
    }

    override suspend fun updateUser(user: User): Result<Nothing?> = withContext(ioDispatcher) {
        try {
            val response = server.apiService.updateUser(user.email, user)
            if (response.isSuccessful) {
                return@withContext Success(data = null)
            } else {
                return@withContext Error(HttpException(response))
            }
        } catch (exception: ConnectException) {
            return@withContext Error(exception)
        }
    }
}
