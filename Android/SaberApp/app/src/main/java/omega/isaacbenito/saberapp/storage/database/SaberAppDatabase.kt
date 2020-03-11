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

package omega.isaacbenito.saberapp.storage.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import omega.isaacbenito.saberapp.storage.database.dao.UserDao
import omega.isaacbenito.saberapp.entities.User

@Database(entities = [User::class], version = 1)
abstract class SaberAppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao



    companion object {
        @Volatile
        private var instance: SaberAppDatabase? = null

        fun getInstance(context: Context): SaberAppDatabase {
            synchronized(this) {
                var _instance = instance

                val PREPOPULATE_DATA = listOf(
                    User(
                        1L,
                        "nom",
                        "cognoms",
                        "nickname",
                        "email@email.com",
                        "escola",
                        'A'
                    ),
                    User(
                        2L,
                        "nom2",
                        "cognoms2",
                        "nickname2",
                        "email2@email.com",
                        "escola",
                        'A'
                    )
                )

                if (_instance == null) {
                    _instance = Room.databaseBuilder(
                        context.applicationContext,
                        SaberAppDatabase::class.java,
                        "SaberAppDatabase"
                    )
                    .fallbackToDestructiveMigration()
                    .addCallback(object: Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            ioThred {
                                getInstance(context).userDao().insertData(PREPOPULATE_DATA)
                            }
                        }
                    })
                    .build()
                    instance = _instance
                }

                return _instance
            }
        }
    }
}