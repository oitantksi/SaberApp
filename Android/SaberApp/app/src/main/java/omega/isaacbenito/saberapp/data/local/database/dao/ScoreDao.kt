package omega.isaacbenito.saberapp.data.local.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import omega.isaacbenito.saberapp.data.entities.Score
import omega.isaacbenito.saberapp.data.entities.ScoreWithUserAndMateria

/**
 * TODO
 *
 * @author Isaac Benito
 **/
@Dao
abstract class ScoreDao : BaseDao<Score>() {

    @Query("SELECT SUM(score) FROM scores WHERE userId=:userId")
    abstract fun getScoreByUser(userId: Long): LiveData<Int>

    @Query("SELECT score FROM scores WHERE userId=:userId AND materiaId=:materiaId")
    abstract fun getMateriaScoreByUser(materiaId: Long, userId: Long): LiveData<Int>

    @Query("SELECT * FROM scores WHERE materiaId=:materiaId")
    abstract fun getAllScoresByMateria(materiaId: Long): LiveData<List<Score>>

    @Query("SELECT * FROM scores WHERE userId=:userId")
    abstract fun getAllMateriaScoresByUser(userId: Long): LiveData<List<Score>>

    @Query("SELECT * FROM scores WHERE userId IN (SELECT userId FROM users WHERE centre=:centre)")
    abstract fun getAllScoresByCentre(centre: String): LiveData<List<Score>>

    @Query("SELECT * FROM scores LEFT JOIN materies ON materiaId=materies.id LEFT JOIN users ON userId=users.id ORDER BY materiaId, score")
    abstract fun getScores(): LiveData<List<ScoreWithUserAndMateria>>

}