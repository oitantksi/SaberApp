package omega.isaacbenito.saberapp.data.local

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import omega.isaacbenito.saberapp.data.AppLocalDataSource
import omega.isaacbenito.saberapp.data.Result
import omega.isaacbenito.saberapp.data.Result.Error
import omega.isaacbenito.saberapp.data.Result.Success
import omega.isaacbenito.saberapp.data.entities.*
import omega.isaacbenito.saberapp.data.local.database.dao.*

class SaberAppLocalDataSource internal constructor(
    private val userDao: UserDao,
    private val centreDao: CentreDao,
    private val materiaDao: MateriaDao,
    private val preguntaDao: PreguntaDao,
    private val respostaDao: RespostaDao,
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
            Success(user)
        } catch (e: Exception) {
            Error(e)
        }
    }

    override suspend fun getUserId(email: String): Result<Long> = withContext(ioDispatcher){
        return@withContext try {
            Success(userDao.getUserId(email))
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

    override suspend fun getMateries(): Result<LiveData<List<Materia>>> {
        return try {
            Success(materiaDao.getMateries())
        } catch (e: Exception) {
            Error(e)
        }
    }

    override suspend fun updateMateries(materies: List<Materia>): Result<Unit> = withContext(ioDispatcher) {
        return@withContext try {
            Success(materiaDao.save(materies))
        } catch (e: Exception) {
            Error(e)
        }
    }

    override suspend fun getPreguntesAmbRespostaByUser(userId: Long): Result<LiveData<List<PreguntaAmbResposta>>> {
        return try {
            Success(preguntaDao.getPreguntesAmbRespostaByUser(userId))
        } catch (e: Exception) {
            Error(e)
        }
    }

    override suspend fun getPreguntes(): Result<LiveData<List<Pregunta>>> {
        return try {
            Success(preguntaDao.getPreguntes())
        } catch (e: Exception) {
            Error(e)
        }
    }

    override suspend fun savePreguntes(preguntes: List<Pregunta>): Result<Unit> = withContext(ioDispatcher){
        return@withContext try {
            Success(preguntaDao.save(preguntes))
        } catch (e: Exception) {
            Error(e)
        }
    }

    override suspend fun saveRespostes(respostes: List<Resposta>): Result<Unit> = withContext(ioDispatcher){
        return@withContext try {
            Success(respostaDao.save(respostes))
        } catch (e: Exception) {
            Error(e)
        }
    }

    override suspend fun saveResposta(resposta: Resposta): Result<Unit> = withContext(ioDispatcher){
        return@withContext try {
            Success(respostaDao.save(resposta))
        } catch (e: Exception) {
            Error(e)
        }
    }

    override suspend fun getResposta(userId: Long, preguntaId: Long): Result<Resposta> = withContext(ioDispatcher){
        return@withContext try {
            Success(respostaDao.getResposta(userId, preguntaId))
        } catch (e: Exception) {
            Error(e)
        }
    }
}