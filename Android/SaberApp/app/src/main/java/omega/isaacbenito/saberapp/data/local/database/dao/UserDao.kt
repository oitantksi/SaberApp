/*
 * This file is part of SaberApp.
 *
 *     SaberApp is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     SaberApp is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with SaberApp.  If not, see <https://www.gnu.org/licenses/>.
 */

package omega.isaacbenito.saberapp.data.local.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import omega.isaacbenito.saberapp.data.entities.User

@Dao
abstract class UserDao {

    @Query("SELECT * FROM users WHERE email=:userMail")
    abstract fun get(userMail: String): LiveData<User>

    @Query("SELECT email FROM users")
    abstract suspend fun getAllUsersEmail(): List<String>

    @Query(value = "SELECT * FROM users")
    abstract fun getAll(): LiveData<List<User>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insert(user: User): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insert(userList: List<User>): List<Long>

    @Update
    abstract suspend fun update(user: User)

    @Update
    abstract suspend fun update(userList: List<User>)

    @Transaction
    open suspend fun save(user: User) {
        if (insert(user) == -1L) {
            update(user)
        }
    }

    @Transaction
    open suspend fun save(userList: List<User>) {
        val updateList = mutableListOf<User>()
        userList.forEach {
            if (insert(it) == -1L) {
                updateList.add(it)
            }
        }

        update(updateList)

    }

    @Transaction
    open suspend fun save(vararg users: User) {
        val updateList = mutableListOf<User>()
        for (user in users) {
            if (insert(user) == -1L) {
                updateList.add(user)
            }
        }
        update(updateList)
    }
}