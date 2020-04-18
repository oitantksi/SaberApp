package omega.isaacbenito.saberapp.data.repos.impl

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.work.*
import omega.isaacbenito.saberapp.data.AppLocalDataSource
import omega.isaacbenito.saberapp.data.AppRemoteDataSource
import omega.isaacbenito.saberapp.data.Result
import omega.isaacbenito.saberapp.data.di.DataModule
import omega.isaacbenito.saberapp.data.entities.Materia
import omega.isaacbenito.saberapp.data.entities.Pregunta
import omega.isaacbenito.saberapp.data.entities.PreguntaAmbResposta
import omega.isaacbenito.saberapp.data.entities.Resposta
import omega.isaacbenito.saberapp.data.repos.JocPreguntesRepository
import omega.isaacbenito.saberapp.data.workers.JocPreguntesWorker
import omega.isaacbenito.saberapp.data.workers.JocPreguntesWorker.Companion.PREGUNTA_ID
import omega.isaacbenito.saberapp.data.workers.JocPreguntesWorker.Companion.USER_ID
import omega.isaacbenito.saberapp.utils.NetworkUtils
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Implementació del repoitori de dades del Joc de Preguntes
 *
 * @author Isaac Benito
 **/
class JocPreguntesRepositoryImpl @Inject constructor (
    val context: Context,
    @DataModule.LocalDataSource private val localDataSource: AppLocalDataSource,
    @DataModule.RemoteDataSource private val remoteDataSource: AppRemoteDataSource
) : JocPreguntesRepository {

    @Inject
    lateinit var networkUtils: NetworkUtils

    override suspend fun getPreguntesAmbRespostaByUser(userAccountIdentifier: String): Result<LiveData<List<PreguntaAmbResposta>>> {

        val userId = when(val result = localDataSource.getUserId(userAccountIdentifier)) {
            is Result.Success -> result.data
            is Result.Error -> throw result.exception
            else -> throw IllegalStateException()
        }

        when (val remoteResult = remoteDataSource.getPreguntes()) {
            is Result.Success -> {
                localDataSource.savePreguntes(remoteResult.data.map { it.getPregunta() })
            }
            is Result.Error -> Timber.w(
                "No s'han pogut recuperar les preguntes del servidor:\n${remoteResult.exception}"
            )

            else -> throw IllegalStateException()
        }

        when (val remoteResult = remoteDataSource.getRespostes(userId)) {
            is Result.Success -> {
                localDataSource.saveRespostes(remoteResult.data)
            }
            is Result.Error -> Timber.w(
                "No s'han pogut recuperar les respostes del servidor:\n${remoteResult.exception}"
            )

            else -> throw IllegalStateException()
        }

        return localDataSource.getPreguntesAmbRespostaByUser(userId)
    }

    override suspend fun getPreguntes() : Result<LiveData<List<Pregunta>>>{
        return localDataSource.getPreguntes()
    }


    override suspend fun getMateries(): Result<LiveData<List<Materia>>> {
        when(val remoteResult = remoteDataSource.getMateries()) {
            is Result.Success -> localDataSource.updateMateries(remoteResult.data)
            is Result.Error -> Timber.d("No s'han pogut recuperar les materies del servidor")
            else -> throw IllegalStateException()
        }

        return localDataSource.getMateries()
    }

    override suspend fun setResposta(resposta: Resposta): Result<Unit> {
        localDataSource.saveResposta(resposta)

        var remoteResult: Result<Unit?>? = null
        if (networkUtils.isNetworkConnected()) {
            remoteResult = remoteDataSource.setResposta(resposta)

            if (remoteResult !is Result.Success ) {
                remoteResult = null
            }
        }

        if (remoteResult == null) {
            Timber.d("Unable to connect. Creating WorkManager")

            val workerConstraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val updateWork = OneTimeWorkRequestBuilder<JocPreguntesWorker>()
                .setConstraints(workerConstraints)
                .setBackoffCriteria(
                    BackoffPolicy.LINEAR,
                    OneTimeWorkRequest.MIN_BACKOFF_MILLIS,
                    TimeUnit.MILLISECONDS)
                .setInputData(workDataOf(
                    PREGUNTA_ID to resposta.preguntaId,
                    USER_ID to resposta.userId
                ))
                .build()

            WorkManager.getInstance(context).enqueueUniqueWork(
                JocPreguntesWorker.JOC_PREGUNTES_WORK,
                ExistingWorkPolicy.REPLACE,
                updateWork).result
        }

        return Result.Error(Exception())
    }
}