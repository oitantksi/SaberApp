package omega.isaacbenito.saberapp.data.di

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import omega.isaacbenito.saberapp.data.AppLocalDataSource
import omega.isaacbenito.saberapp.data.AppRemoteDataSource
import omega.isaacbenito.saberapp.data.local.SaberAppLocalDataSource
import omega.isaacbenito.saberapp.data.local.database.SaberAppDatabase
import omega.isaacbenito.saberapp.data.prefs.PrefStorage
import omega.isaacbenito.saberapp.data.prefs.PrefStorageImpl
import omega.isaacbenito.saberapp.data.remote.SaberAppRemoteDataSource
import omega.isaacbenito.saberapp.data.remote.server.SaberAppServer
import omega.isaacbenito.saberapp.data.repos.CentreRepository
import omega.isaacbenito.saberapp.data.repos.JocPreguntesRepository
import omega.isaacbenito.saberapp.data.repos.UserRepository
import omega.isaacbenito.saberapp.data.repos.impl.CentreRepositoryImpl
import omega.isaacbenito.saberapp.data.repos.impl.JocPreguntesRepositoryImpl
import omega.isaacbenito.saberapp.data.repos.impl.UserRepositoryImpl
import omega.isaacbenito.saberapp.data.workers.JocPreguntesWorker
import omega.isaacbenito.saberapp.data.workers.ProfileEditWorker
import omega.isaacbenito.saberapp.di.worker.AppWorkerFactory
import omega.isaacbenito.saberapp.di.worker.WorkerKey
import javax.inject.Qualifier
import javax.inject.Singleton

/**
 * M
 */
@Module
abstract class DataModule {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class RemoteDataSource

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class LocalDataSource

    @Binds
    abstract fun bindUserRepository(repo: UserRepositoryImpl) : UserRepository

    @Binds
    abstract fun bindCentreRepository(repo: CentreRepositoryImpl) : CentreRepository

    @Binds
    abstract fun bindPrefStorage(storage: PrefStorageImpl) : PrefStorage


    @Binds
    abstract fun bindJocPreguntesRepository(repo: JocPreguntesRepositoryImpl) : JocPreguntesRepository

    @SuppressWarnings("unused")
    @Binds
    @IntoMap
    @WorkerKey(ProfileEditWorker::class)
    abstract fun bindProfileEditWorker(factory: ProfileEditWorker.Factory): AppWorkerFactory

    @SuppressWarnings("unused")
    @Binds
    @IntoMap
    @WorkerKey(JocPreguntesWorker::class)
    abstract fun bindJocPreguntesWorker(factory: JocPreguntesWorker.Factory): AppWorkerFactory

    @Module
    companion object {
        @JvmStatic
        @Singleton
        @RemoteDataSource
        @Provides
        fun provideRemoteDataSource(
            server: SaberAppServer
        ): AppRemoteDataSource {
            return SaberAppRemoteDataSource(
                server
            )
        }

        @JvmStatic
        @Singleton
        @Provides
        fun provideServer(): SaberAppServer {
            return SaberAppServer()
        }

        @JvmStatic
        @Singleton
        @LocalDataSource
        @Provides
        fun provideLocalDataSource(
            database: SaberAppDatabase,
            ioDispatcher: CoroutineDispatcher
        ): AppLocalDataSource {
            return SaberAppLocalDataSource(
                database.userDao(),
                database.centreDao(),
                database.materiaDao(),
                database.preguntaDao(),
                database.respostaDao(),
                ioDispatcher
            )
        }

        @JvmStatic
        @Singleton
        @Provides
        fun provideDataBase(context: Context): SaberAppDatabase {
            return Room.databaseBuilder(
                context,
                SaberAppDatabase::class.java,
                "SaberAppDatabase"
            )
                .fallbackToDestructiveMigration()
                    // Prepopulate DB only used for testing
//                .addCallback(object : RoomDatabase.Callback() {
//                    override fun onCreate(db: SupportSQLiteDatabase) {
//                        super.onCreate(db)
//                        CoroutineScope(Dispatchers.IO).launch {
//                            val database = provideDataBase(context)
//                            database.userDao().insert(data.getPrepopulateUsers())
//                            database.materiaDao().insert(data.getPrepopulateMateries())
//                            database.preguntaDao().insert(data.getPrepopulatePreguntes())
//                            database.preguntaDao().insertRespostes(data.getPrepopulateAnswers())
//                        }
//                    }
//                })
                .build()
        }

        @JvmStatic
        @Singleton
        @Provides
        fun provideIoDispatcher() = Dispatchers.IO
    }
}

