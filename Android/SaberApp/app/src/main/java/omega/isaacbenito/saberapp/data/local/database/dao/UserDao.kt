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
import omega.isaacbenito.saberapp.data.entities.ProfilePicture
import omega.isaacbenito.saberapp.data.entities.User
import omega.isaacbenito.saberapp.data.entities.UserWithPicture

@Dao
abstract class UserDao {

    @Query("SELECT * FROM users WHERE email=:userMail")
    abstract fun get(userMail: String): LiveData<User>

    @Query("SELECT * FROM users LEFT JOIN profile_pictures ON id=userId WHERE email=:userMail")
    abstract fun getUserWithPicture(userMail: String): LiveData<UserWithPicture>

    @Query("SELECT * FROM users WHERE id=:userId")
    abstract fun get(userId: Long): User

    @Query("SELECT id FROM users WHERE email=:userMail")
    abstract fun getUserId(userMail: String): Long

    @Query(value = "SELECT * FROM users")
    abstract fun getAll(): LiveData<List<User>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insert(user: User): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insert(userList: List<User>): List<Long>

    @Update
    abstract fun update(user: User)

    @Update
    abstract fun update(userList: List<User>)

    @Transaction
    open fun save(user: User) {
        if (insert(user) == -1L) {
            update(user)
        }
    }

    @Transaction
    open fun save(userList: List<User>) {
        val updateList = mutableListOf<User>()
        userList.forEach {
            if (insert(it) == -1L) {
                updateList.add(it)
            }
        }

        update(updateList)

    }

    @Transaction
    open fun save(vararg users: User) {
        val updateList = mutableListOf<User>()
        for (user in users) {
            if (insert(user) == -1L) {
                updateList.add(user)
            }
        }
        update(updateList)
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insert(picture: ProfilePicture): Long

    @Update
    abstract fun update(picture: ProfilePicture)

    @Transaction
    open fun save(userWithPicture: UserWithPicture) {
        val user = userWithPicture.user
        val picture = userWithPicture.profilePicture
        if (insert(user) == -1L) {
            update(user)
        }
        if (picture != null) {
            save(picture)
        }
    }

    @Transaction
    open fun save(picture: ProfilePicture) {
        if (insert(picture) == -1L) {
            update(picture)
        }
    }
}
