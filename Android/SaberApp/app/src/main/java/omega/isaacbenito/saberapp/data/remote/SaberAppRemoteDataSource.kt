package omega.isaacbenito.saberapp.data.remote

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import omega.isaacbenito.saberapp.data.AppRemoteDataSource
import omega.isaacbenito.saberapp.data.Result
import omega.isaacbenito.saberapp.data.Result.Error
import omega.isaacbenito.saberapp.data.Result.Success
import omega.isaacbenito.saberapp.data.entities.*
import omega.isaacbenito.saberapp.data.remote.server.SaberAppServer
import retrofit2.HttpException
import retrofit2.Response
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

    /**
     * Realitza una crida al servidor per a emmagatzemar dades en un fil d'IO i evalua la resposta.
     *
     * @param serverPostAction crida a realitzar al servidor
     * @return [Success] en cas que la resposta del servidor sigui satisfactoria
     * [Error] en cas contrari o en cas que es produeixi una [ConnectException]
     */
    private suspend fun postData(serverPostAction: suspend () -> Response<Unit>) : Result<Nothing?> = withContext(ioDispatcher){
        return@withContext try {
            val response = serverPostAction()
            if (response.isSuccessful) {
                Success(data = null)
            } else {
                Error(HttpException(response))
            }
        } catch (exception: ConnectException) {
            Error(exception)
        }
    }

    /**
     * Realitza una crida al servidor per a obtenir dades en un fil d'IO i evalua la resposta.
     *
     * @param T classe de les dades a obtindre
     * @param serverGetAction crida a realitzar al servidor
     * @return [Success] embolcallant un objecte [T] en cas que la resposta del servidor
     * sigui satisfactoria [Error] en cas contrari o en cas que es produeixi una [ConnectException]
     */
    private suspend fun <T: Any> getData(serverGetAction: suspend () -> Response<out T>) : Result<out T> = withContext(ioDispatcher) {
        return@withContext try {
            val response = serverGetAction()
            if (response.isSuccessful && response.body() != null) {
                Success((response.body()!!) as T)
            } else {
                Error(Exception(response.message()))
            }
        } catch (e: ConnectException) {
            Error(e)
        }
    }

    override suspend fun getAllCentres(): Result<List<Centre>>
            = getData { server.apiService.getCentres() }

    override suspend fun getUser(email: String): Result<User>
            = getData { server.apiService.getUser(email) }

    //TODO GetAllUsers

    override suspend fun updateUser(userEmail: String, user: User): Result<Nothing?>
            = postData { server.apiService.updateUser(userEmail, user) }

    override suspend fun updatePassword(
        userEmail: String,
        oldPassword: String,
        newPassword: String
    ) : Result<Nothing?> = postData {
        server.apiService.updatePassword(userEmail, User.UpdatePasswordDto(oldPassword, newPassword))
    }

    override suspend fun getMateries(): Result<List<Materia>>
            = getData { server.apiService.getMateries() }

    override suspend fun getPreguntes(): Result<List<Pregunta.ServerQuest>>
            = getData { server.apiService.getPreguntes() }

    override suspend fun getRespostes(userId: Long): Result<List<Resposta>>
            = getData { server.apiService.getRespostesAlumne(userId) }

    override suspend fun setResposta(resposta: Resposta): Result<Nothing?>
            = postData { server.apiService.postResposta(resposta.getDto()) }

    override suspend fun getPuntuacions(): Result<List<Score.Dto>> =
        getData { server.apiService.getPuntuacions() }
}
