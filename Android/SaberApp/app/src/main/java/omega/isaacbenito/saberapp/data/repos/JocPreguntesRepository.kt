package omega.isaacbenito.saberapp.data.repos

import androidx.lifecycle.LiveData
import omega.isaacbenito.saberapp.data.Result
import omega.isaacbenito.saberapp.data.entities.*

/**
 * Repoitori de dades del Joc de Preguntes
 *
 * @author Isaac Benito
 **/
interface JocPreguntesRepository {

    suspend fun getPreguntesAmbRespostaByUser(userAccountIdentifier: String): Result<LiveData<List<PreguntaAmbResposta>>>

    suspend fun getMateries() : Result<LiveData<List<Materia>>>

    suspend fun setResposta(resposta: Resposta) : Result<Unit>

    suspend fun getPreguntes(): Result<LiveData<List<Pregunta>>>

    suspend fun getUserScore(userAccountIdentifier: String): Result<LiveData<Int>>

    suspend fun getScores(): Result<LiveData<List<ScoreWithUserAndMateria>>>
}