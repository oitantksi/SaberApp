package omega.isaacbenito.saberapp.data.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import omega.isaacbenito.saberapp.data.AppLocalDataSource
import omega.isaacbenito.saberapp.data.AppRemoteDataSource
import omega.isaacbenito.saberapp.data.di.DataModule
import omega.isaacbenito.saberapp.data.entities.User
import omega.isaacbenito.saberapp.di.worker.AppWorkerFactory
import javax.inject.Inject
import omega.isaacbenito.saberapp.data.Result as OpResult

/**
 * Realitza l'actulització de dades de l'usuari en el servidor quan es donin les condicions necessàries
 *
 * @author Isaac Benito
 * @property localDataSource
 * @property remoteDataSource
 * @param context
 * @param workerParameters
 */
class ProfileEditWorker(
    context: Context,
    workerParameters: WorkerParameters,
    @DataModule.LocalDataSource
    private val localDataSource: AppLocalDataSource,
    @DataModule.RemoteDataSource
    private val remoteDataSource: AppRemoteDataSource
) : CoroutineWorker(context, workerParameters) {



    companion object {
        const val EDIT_PROFILE_WORK = "EDIT_PROFILE_WORK"
        const val EDIT_PASSWORD_WORK = "EDIT_PASSWORD_WORK"
        const val PROFILE_EDIT_ACTION = "PROFILE_EDIT_ACTION"
        const val ACTION_UPDATE_USER_DATA = "ACTION_UPDATE_USER_DATA"
        const val ACTION_UPDATE_USER_PASSWORD = "ACTION_UPDATE_USER_PASSWORD"
        const val USER_EMAIL_KEY = "USER_EMAIL_KEY"
        const val USER_DATA_KEY = "USER_DATA_KEY"
        const val USER_OLD_PASSWORD = "USER_OLD_PASSWORD"
        const val USER_NEW_PASSWORD = "USER_NEW_PASSWORD"
    }

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        val userEmail = inputData.getString(USER_EMAIL_KEY) ?: throw NullPointerException()

        lateinit var remoteResponse : OpResult<Unit?>

        when (inputData.getString(PROFILE_EDIT_ACTION) ?: throw NullPointerException()) {
            ACTION_UPDATE_USER_DATA -> {
                val user = User(inputData.getString(USER_DATA_KEY) ?: throw NullPointerException())
                remoteResponse = remoteDataSource.updateUser(userEmail, user)
            }
            ACTION_UPDATE_USER_PASSWORD -> {
                val oldPassword = inputData.getString(USER_OLD_PASSWORD) ?: throw NullPointerException()
                val newPassword = inputData.getString(USER_NEW_PASSWORD) ?: throw NullPointerException()
                remoteResponse = remoteDataSource.updatePassword(userEmail, oldPassword, newPassword)
            }
        }

        return@withContext when(remoteResponse) {
            is OpResult.Success -> Result.success()
            is OpResult.Error -> Result.retry()
            else -> Result.retry()
        }
    }

    class Factory @Inject constructor(
        @DataModule.LocalDataSource
        private val localDataSource: AppLocalDataSource,
        @DataModule.RemoteDataSource
        private val remoteDataSource: AppRemoteDataSource
    ): AppWorkerFactory {
        override fun create(
            appContext: Context,
            workerParameters: WorkerParameters
        ): ListenableWorker {
            return ProfileEditWorker(
                appContext,
                workerParameters,
                localDataSource,
                remoteDataSource
            )
        }
    }
}