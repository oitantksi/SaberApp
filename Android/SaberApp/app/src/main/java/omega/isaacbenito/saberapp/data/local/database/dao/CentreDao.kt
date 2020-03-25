package omega.isaacbenito.saberapp.data.local.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import omega.isaacbenito.saberapp.data.entities.Centre

@Dao
abstract class CentreDao : BaseDao<Centre>() {

    @Query("SELECT * FROM centres")
    abstract fun getAllCentres(): LiveData<List<Centre>>

    @Insert(onConflict = REPLACE)
    abstract fun updateAllCentres(centres: List<Centre>)
}