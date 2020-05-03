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
    private val scoreDao: ScoreDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : AppLocalDataSource {

    private suspend fun <T : Any> dbOperation(dbOperation: suspend () -> T): Result<out T> =
        withContext(ioDispatcher) {
            return@withContext try {
                Success(dbOperation())
            } catch (e: Exception) {
                Error(e)
            }
        }

    private suspend fun <T : Any> dbLiveDataOperation(dbOperation: suspend () -> T): Result<out T> {
        return try {
            Success(dbOperation())
        } catch (e: Exception) {
            Error(e)
        }
    }

    override suspend fun getAllCentres(): Result<LiveData<List<Centre>>> =
        dbLiveDataOperation { centreDao.getAllCentres() }

    override suspend fun updateCentres(centres: List<Centre>): Result<Unit> =
        dbOperation { centreDao.save(centres) }

    override suspend fun getUser(email: String): Result<LiveData<User>> =
        dbLiveDataOperation { userDao.get(email) }

    override suspend fun getUser(id: Long): Result<User> = dbOperation { userDao.get(id) }

    override suspend fun getUserWithPicture(email: String): Result<LiveData<UserWithPicture>> =
        dbLiveDataOperation { userDao.getUserWithPicture(email) }

    override suspend fun getUserId(email: String): Result<Long> =
        dbOperation { userDao.getUserId(email) }

    override suspend fun saveUser(user: User): Result<Unit> = dbOperation { userDao.save(user) }

    override suspend fun savePicture(picture: ProfilePicture): Result<Unit> =
        dbOperation { userDao.save(picture) }

    override suspend fun saveUser(userWithPicture: UserWithPicture): Result<Unit> =
        dbOperation { userDao.save(userWithPicture) }

    override suspend fun saveUsers(users: List<User>): Result<Unit> =
        dbOperation { userDao.save(users) }

    override suspend fun getMateries(): Result<LiveData<List<Materia>>> =
        dbLiveDataOperation { materiaDao.getMateries() }

    override suspend fun updateMateries(materies: List<Materia>): Result<Unit> =
        dbOperation { materiaDao.save(materies) }

    override suspend fun getPreguntesAmbRespostaByUser(userId: Long): Result<LiveData<List<PreguntaAmbResposta>>> =
        dbLiveDataOperation { preguntaDao.getPreguntesAmbRespostaByUser(userId) }

    override suspend fun getPreguntes(): Result<LiveData<List<Pregunta>>> =
        dbLiveDataOperation { preguntaDao.getPreguntes() }

    override suspend fun savePreguntes(preguntes: List<Pregunta>): Result<Unit> =
        dbOperation { preguntaDao.save(preguntes) }

    override suspend fun saveRespostes(respostes: List<Resposta>): Result<Unit> =
        dbOperation { respostaDao.save(respostes) }

    override suspend fun saveResposta(resposta: Resposta): Result<Unit> =
        dbOperation { respostaDao.save(resposta) }

    override suspend fun getResposta(userId: Long, preguntaId: Long): Result<Resposta> =
        dbOperation { respostaDao.getResposta(userId, preguntaId) }

    override suspend fun saveScores(scores: List<Score>): Result<Unit> =
        dbOperation { scoreDao.save(scores) }

    override suspend fun getUserScore(userId: Long): Result<LiveData<Int>> =
        dbOperation { scoreDao.getScoreByUser(userId) }

    override suspend fun getScores(): Result<LiveData<List<ScoreWithUserAndMateria>>> =
        dbLiveDataOperation { scoreDao.getScores() }
}
