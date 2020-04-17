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
import omega.isaacbenito.saberapp.di.worker.AppWorkerFactory
import timber.log.Timber
import javax.inject.Inject
import omega.isaacbenito.saberapp.data.Result as OpResult


/**
 * Realitza l'actulització de dades del joc de preguntes en el servidor
 * quan es donin les condicions necessàries
 *
 * @author Isaac Benito
 * @property localDataSource
 * @property remoteDataSource
 * @param context
 * @param workerParameters
 **/
class JocPreguntesWorker (
    context: Context,
    workerParameters: WorkerParameters,
    @DataModule.LocalDataSource
    private val localDataSource: AppLocalDataSource,
    @DataModule.RemoteDataSource
    private val remoteDataSource: AppRemoteDataSource
) : CoroutineWorker(context, workerParameters) {

    companion object {
        const val JOC_PREGUNTES_WORK = "JOC_PREGUNTES_WORK"
        const val PREGUNTA_ID = "PREGUNTA_ID"
        const val USER_ID = "USER_ID"
    }

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        val preguntaId = inputData.getLong(PREGUNTA_ID, 0)
        val userId = inputData.getLong(USER_ID, 0)

        when (val resposta = localDataSource.getResposta(userId, preguntaId)) {
            is OpResult.Success -> {
                when (remoteDataSource.setResposta(resposta.data)) {
                    is OpResult.Success -> Result.success()
                    else -> Result.retry()
                }
            }
            else -> Result.retry()
        }
    }

    class Factory @Inject constructor(
        @DataModule.LocalDataSource
        private val localDataSource: AppLocalDataSource,
        @DataModule.RemoteDataSource
        private val remoteDataSource: AppRemoteDataSource
    ) : AppWorkerFactory {
        override fun create(
            appContext: Context,
            workerParameters: WorkerParameters
        ): ListenableWorker {
            return JocPreguntesWorker(
                appContext,
                workerParameters,
                localDataSource,
                remoteDataSource
            )
        }
    }
}