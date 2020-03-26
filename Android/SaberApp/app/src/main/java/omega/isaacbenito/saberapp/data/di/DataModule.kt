package omega.isaacbenito.saberapp.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import omega.isaacbenito.saberapp.data.AppLocalDataSource
import omega.isaacbenito.saberapp.data.AppRemoteDataSource
import omega.isaacbenito.saberapp.data.local.SaberAppLocalDataSource
import omega.isaacbenito.saberapp.data.local.database.SaberAppDatabase
import omega.isaacbenito.saberapp.data.remote.SaberAppRemoteDataSource
import omega.isaacbenito.saberapp.data.remote.server.SaberAppServer
import javax.inject.Qualifier
import javax.inject.Singleton

/**
 * M
 */
@Module
object DataModule {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class RemoteDataSource

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class LocalDataSource

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
            .build()
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO
}

