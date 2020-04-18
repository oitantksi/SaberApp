package omega.isaacbenito.saberapp.data.local.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import omega.isaacbenito.saberapp.data.entities.Pregunta
import omega.isaacbenito.saberapp.data.entities.PreguntaAmbResposta

/**
 * DAO per a realitzar operacions en la base de dades amb objectes [Pregunta] i [PreguntaAmbResposta]
 *
 * @author Isaac Benito
 **/
@Dao
abstract class PreguntaDao : BaseDao<Pregunta>() {

    @Query("SELECT * FROM preguntes as p LEFT JOIN (SELECT * FROM respostes WHERE userId=:userId) as r ON r.preguntaId=p.id")
    abstract fun getPreguntesAmbRespostaByUser(userId: Long): LiveData<List<PreguntaAmbResposta>>

    @Query("SELECT * FROM preguntes")
    abstract fun getPreguntes(): LiveData<List<Pregunta>>
}