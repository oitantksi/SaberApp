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