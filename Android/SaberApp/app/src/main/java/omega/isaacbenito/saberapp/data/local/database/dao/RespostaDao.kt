package omega.isaacbenito.saberapp.data.local.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import omega.isaacbenito.saberapp.data.entities.Resposta

/**
 * DAO per a realitzar operacions en la base de dades amb objectes [Resposta]
 *
 * @author Isaac Benito
 **/
@Dao
abstract class RespostaDao : BaseDao<Resposta>() {

    @Query("SELECT * FROM respostes WHERE userId=:userId AND preguntaId=:preguntaId")
    abstract fun getResposta(userId: Long, preguntaId: Long) : Resposta

    @Query("SELECT * FROM respostes WHERE userId=:userId")
    abstract fun getRespostesByUser(userId: Long): LiveData<List<Resposta>>
}