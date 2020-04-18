package omega.isaacbenito.saberapp.data.local.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import omega.isaacbenito.saberapp.data.entities.Materia

/**
 * DAO per a realitzar operacions en la base de dades amb objectes [Materia]
 *
 * @author Isaac Benito
 **/
@Dao
abstract class MateriaDao : BaseDao<Materia>() {

    @Query("SELECT * FROM materies")
    abstract fun getMateries(): LiveData<List<Materia>>
}