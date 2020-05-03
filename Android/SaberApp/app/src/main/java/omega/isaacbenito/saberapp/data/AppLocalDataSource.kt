package omega.isaacbenito.saberapp.data

import androidx.lifecycle.LiveData
import omega.isaacbenito.saberapp.data.entities.*

interface AppLocalDataSource {

    suspend fun getAllCentres(): Result<LiveData<List<Centre>>>

    suspend fun updateCentres(centres: List<Centre>): Result<Unit>

    suspend fun getUser(email: String): Result<LiveData<User>>

    suspend fun getUser(id: Long): Result<User>

    suspend fun getUserWithPicture(email: String): Result<LiveData<UserWithPicture>>

    suspend fun getUserId(email: String) : Result<Long>

    suspend fun saveUser(user: User): Result<Unit>

    suspend fun saveUsers(users: List<User>): Result<Unit>

    suspend fun savePicture(picture: ProfilePicture): Result<Unit>

    suspend fun saveUser(userWithPicture: UserWithPicture): Result<Unit>

    suspend fun getMateries() : Result<LiveData<List<Materia>>>

//    suspend fun getMateriaById(id: Long) : Result<Materia>
//
//    suspend fun getMateriaByName(name: String) : Result<Materia>

    suspend fun updateMateries(materies: List<Materia>) : Result<Unit>

    suspend fun getPreguntesAmbRespostaByUser(userId: Long) : Result<LiveData<List<PreguntaAmbResposta>>>

//    suspend fun getPregunta(id: Long) : Result<Pregunta>

    suspend fun savePreguntes(preguntes: List<Pregunta>) : Result<Unit>

    suspend fun saveRespostes(respostes: List<Resposta>) : Result<Unit>

    suspend fun getPreguntes(): Result<LiveData<List<Pregunta>>>

    suspend fun saveResposta(resposta: Resposta) : Result<Unit>

    suspend fun getResposta(userId: Long, preguntaId: Long) : Result<Resposta>

    suspend fun saveScores(scores: List<Score>): Result<Unit>

    suspend fun getUserScore(userId: Long): Result<LiveData<Int>>

    suspend fun getScores(): Result<LiveData<List<ScoreWithUserAndMateria>>>
}