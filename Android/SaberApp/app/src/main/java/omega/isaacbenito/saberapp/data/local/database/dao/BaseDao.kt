package omega.isaacbenito.saberapp.data.local.database.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Transaction
import androidx.room.Update

abstract class BaseDao<T> {

    @Insert(onConflict = IGNORE)
    abstract suspend fun insert(obj: T): Long

    @Insert(onConflict = IGNORE)
    abstract suspend fun insert(objList: List<T>): List<Long>

    @Update
    abstract suspend fun update(obj: T)

    @Update
    abstract suspend fun update(objList: List<T>)

    @Transaction
    open suspend fun save(obj: T) {
        if (insert(obj) == -1L) {
            update(obj)
        }
    }

    @Transaction
    open suspend fun save(objList: List<T>) {
        val updateList = mutableListOf<T>()
        objList.forEach {
            if (insert(it) == -1L) {
                updateList.add(it)
            }
        }

        update(updateList)

    }

    @Transaction
    open suspend fun save(vararg objs: T) {
        val updateList = mutableListOf<T>()
        for (obj in objs) {
            if (insert(obj) == -1L) {
                updateList.add(obj)
            }
        }
        update(updateList)
    }

}