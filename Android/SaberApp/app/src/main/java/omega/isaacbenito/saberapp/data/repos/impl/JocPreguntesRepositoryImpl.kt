package omega.isaacbenito.saberapp.data.repos.impl

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.work.*
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import omega.isaacbenito.saberapp.data.AppLocalDataSource
import omega.isaacbenito.saberapp.data.AppRemoteDataSource
import omega.isaacbenito.saberapp.data.Result
import omega.isaacbenito.saberapp.data.di.DataModule
import omega.isaacbenito.saberapp.data.entities.*
import omega.isaacbenito.saberapp.data.repos.JocPreguntesRepository
import omega.isaacbenito.saberapp.data.repos.Utils
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

        val userId = getUserId(userAccountIdentifier)

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
        updateMateries()

        return localDataSource.getMateries()
    }

    private suspend fun updateMateries() {
        when(val remoteResult = remoteDataSource.getMateries()) {
            is Result.Success -> localDataSource.updateMateries(remoteResult.data)
            is Result.Error -> Timber.d("No s'han pogut recuperar les materies del servidor")
            else -> throw IllegalStateException()
        }
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

    override suspend fun getUserScore(userAccountIdentifier: String): Result<LiveData<Int>> {

        val userId = getUserId(userAccountIdentifier)

        updateRemoteScores()

        return localDataSource.getUserScore(userId)
    }

    override suspend fun getScores(): Result<LiveData<List<ScoreWithUserAndMateria>>> {

        updateRemoteScores()

        return localDataSource.getScores()
    }

    private suspend fun updateRemoteScores() = withContext(Dispatchers.IO) {
        val updateJobs = arrayListOf<Deferred<Unit>>()
        updateJobs.add(async { updateUsers() })
        updateJobs.add(async { updateMateries() })
        updateJobs.forEach { it.await() }
        when (val remoteResult = remoteDataSource.getPuntuacions()) {
            is Result.Success -> {
                Timber.d("Saving scores: ${remoteResult.data.map { Score.fromDto(it) }}")
                localDataSource.saveScores(remoteResult.data.map { Score.fromDto(it) })
            }
            is Result.Error -> Timber.w(
                "No s'han pogut recuperar les puntuacions del servidor:\n${remoteResult.exception}"
            )

            else -> throw IllegalStateException()
        }
    }

    private suspend fun updateUsers() {
        when (val remoteResult = remoteDataSource.getAllUsers()) {
            is Result.Success -> {
                localDataSource.saveUsers(remoteResult.data)
                for (user in remoteResult.data) {
                    refreshUserPicture(user.id)
                }
            }
            is Result.Error -> Timber.d("No s'han pogut recuperar els usuaris del servidor")
            else -> throw IllegalStateException()
        }
    }

    private suspend fun refreshUserPicture(userId: Long) {
        when (val remoteResult = remoteDataSource.getUserPicture(userId)) {
            is Result.Success -> Utils.saveRemoteImage(
                userId, remoteResult.data, context.filesDir, localDataSource
            )
            is Result.Error -> Timber.d("No s'ha pogut recuperar la imatge de l'usuari $userId del servidor")
            else -> throw IllegalStateException()
        }
    }

    private suspend fun getUserId(userAccountIdentifier: String): Long {
        return when (val result = localDataSource.getUserId(userAccountIdentifier)) {
            is Result.Success -> result.data
            is Result.Error -> throw result.exception
            else -> throw IllegalStateException()
        }
    }
}
