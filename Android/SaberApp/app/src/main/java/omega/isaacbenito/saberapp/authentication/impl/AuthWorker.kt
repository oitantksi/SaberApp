package omega.isaacbenito.saberapp.authentication.impl

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import omega.isaacbenito.saberapp.data.entities.User
import omega.isaacbenito.saberapp.di.worker.AppWorkerFactory
import timber.log.Timber
import javax.inject.Inject
import omega.isaacbenito.saberapp.authentication.AuthResult.Error as AuthError
import omega.isaacbenito.saberapp.authentication.AuthResult.Success as AuthSuccess

/**
 * Realitza la tasca de login en el servidor quan es donin les condicions necessàries
 *
 * @author Isaac Benito
 * @property authManager
 * @param context
 * @param workerParameters
 */
class AuthWorker @Inject constructor(
    private val appContext: Context,
    private val workerParameters: WorkerParameters,
    private val authManager: AuthenticationManagerImpl
) : CoroutineWorker(appContext, workerParameters) {

    companion object {
        const val AUTH_WORK = "AUTH_WORK"
        const val USER_EMAIL = "USER_EMAIL"
        const val USER_PASSWORD = "USER_PASSWORD"
    }

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        Timber.d("Starting auth work")

        val userMail = inputData.getString(USER_EMAIL) ?: throw NullPointerException()
        val userPassword = inputData.getString(USER_PASSWORD) ?: throw NullPointerException()

        return@withContext when (authManager.serverLogin(User.AuthCredentials(userMail, userPassword))) {
            is AuthSuccess -> Result.success()
            is AuthError -> Result.retry()
        }
    }

    class Factory @Inject constructor(
        private val authManager: AuthenticationManagerImpl
    ): AppWorkerFactory {
        override fun create(
            appContext: Context,
            workerParameters: WorkerParameters
        ): ListenableWorker {
            return AuthWorker(
                appContext,
                workerParameters,
                authManager
            )
        }
    }

}
