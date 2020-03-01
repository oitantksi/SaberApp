package omega.isaacbenito.saberapp.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import omega.isaacbenito.saberapp.database.entities.User

@Dao
interface UserDao {
    @Insert(onConflict = REPLACE)
    fun insert(user: User)

    @Insert
    fun insertData(data: List<User>)

    @Update
    fun update(user: User)

    @Query("SELECT * FROM user WHERE id = :userId")
    fun get(userId: Long) : LiveData<User>
}