package omega.isaacbenito.saberapp.data.repos

import androidx.lifecycle.LiveData
import omega.isaacbenito.saberapp.data.Result
import omega.isaacbenito.saberapp.data.entities.Materia
import omega.isaacbenito.saberapp.data.entities.Pregunta
import omega.isaacbenito.saberapp.data.entities.PreguntaAmbResposta
import omega.isaacbenito.saberapp.data.entities.Resposta

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
}