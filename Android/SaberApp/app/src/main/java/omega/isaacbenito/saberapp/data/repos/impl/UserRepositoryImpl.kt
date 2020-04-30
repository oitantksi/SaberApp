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

package omega.isaacbenito.saberapp.data.repos.impl

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.work.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import omega.isaacbenito.saberapp.data.AppLocalDataSource
import omega.isaacbenito.saberapp.data.AppRemoteDataSource
import omega.isaacbenito.saberapp.data.Result
import omega.isaacbenito.saberapp.data.Result.Error
import omega.isaacbenito.saberapp.data.Result.Success
import omega.isaacbenito.saberapp.data.di.DataModule
import omega.isaacbenito.saberapp.data.entities.User
import omega.isaacbenito.saberapp.data.repos.UserRepository
import omega.isaacbenito.saberapp.data.succeeded
import omega.isaacbenito.saberapp.data.workers.ProfileEditWorker
import omega.isaacbenito.saberapp.utils.NetworkUtils
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton


/**
 * @author Isaac Benito
 *
 * Repositori de dades del tipus [User]
 *
 * @property localDataSource origen de dades local
 * @property remoteDataSource origen de dades remot
 */
@Singleton
class UserRepositoryImpl @Inject constructor(
    val context: Context,
    @DataModule.LocalDataSource private val localDataSource: AppLocalDataSource,
    @DataModule.RemoteDataSource private val remoteDataSource: AppRemoteDataSource
) : UserRepository {
    @Inject
    lateinit var networkUtils: NetworkUtils


    /**
     * Obté un objecte [User] de les fonts de dades de l'aplicació
     *
     * @param userMail correu electrònic de l'usuari
     * @return [Result] amb el resultat de l'operació.
     *  Si [Result] és [Success] encapsula un objecte non-null [User]
     *  Si [Result] és [Error] encapsula l' [Exception] llançada
     */
    override suspend fun getUser(userMail: String, forceUpdate: Boolean): Result<LiveData<User>> {

        // Si hi ha connexió a internet actualitzem les dades de l'usuari
        if (networkUtils.isNetworkConnected()) {
            refreshUser(userMail)
        }

        // Retornem les dades de l'usuari desades en local
        return localDataSource.getUser(userMail)
    }

    override suspend fun getUser(userId: Long): Result<User> {
        return localDataSource.getUser(userId)
    }

    override suspend fun getUserId(userMail: String): Result<Long> {
        return localDataSource.getUserId(userMail)
    }

    /**
     * Actualitza l'origen de dades local amb les dades de l'origen remot.
     */
    private suspend fun refreshUser(userMail: String) {
        when (val remoteResult = remoteDataSource.getUser(userMail)) {
            is Success -> localDataSource.saveUser(remoteResult.data)
            is Error -> Timber.w(
                "No s'ha pogut recuperar l'usuari $userMail del servidor:\n${remoteResult.exception}"
            )

            else -> throw IllegalStateException()
        }
    }


    override suspend fun updateUser(userEmail: String, user: User) {
        val respostaId = localDataSource.saveUser(user)

        var remoteResult: Result<Unit?>? = null
        if (networkUtils.isNetworkConnected()) {
            remoteResult = withContext(Dispatchers.IO) {
                return@withContext remoteDataSource.updateUser(userEmail, user)
            }

            if (!remoteResult.succeeded) {
                remoteResult = null
            }
        }

        if (remoteResult == null) {
            Timber.d("Unable to connect. Creating WorkManager")

            val workerConstraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val updateWork = OneTimeWorkRequestBuilder<ProfileEditWorker>()
                .setConstraints(workerConstraints)
                .setBackoffCriteria(
                    BackoffPolicy.LINEAR,
                    OneTimeWorkRequest.MIN_BACKOFF_MILLIS,
                    TimeUnit.MILLISECONDS)
                .setInputData(workDataOf(
                    ProfileEditWorker.PROFILE_EDIT_ACTION to ProfileEditWorker.ACTION_UPDATE_USER_DATA,
                    ProfileEditWorker.USER_DATA_KEY to user.toString(),
                    ProfileEditWorker.USER_EMAIL_KEY to userEmail))
                .build()

            WorkManager.getInstance(context).enqueueUniqueWork(
                ProfileEditWorker.EDIT_PROFILE_WORK,
                ExistingWorkPolicy.REPLACE,
                updateWork).result
        }


    }

    override suspend fun updateUserPassword(userEmail: String, oldPassword: String, newPassword: String) {
        var remoteResult: Result<Unit?>? = null
        if (networkUtils.isNetworkConnected()) {
            remoteResult = withContext(Dispatchers.IO) {
                return@withContext remoteDataSource.updatePassword(userEmail, oldPassword, newPassword)
            }

            if (!remoteResult.succeeded) {
                remoteResult = null
            }
        }

        if (remoteResult == null) {
            Timber.d("Unable to connect. Creating WorkManager")

            val workerConstraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val updateWork = OneTimeWorkRequestBuilder<ProfileEditWorker>()
                .setConstraints(workerConstraints)
                .setBackoffCriteria(
                    BackoffPolicy.LINEAR,
                    OneTimeWorkRequest.MIN_BACKOFF_MILLIS,
                    TimeUnit.MILLISECONDS)
                .setInputData(workDataOf(
                    ProfileEditWorker.PROFILE_EDIT_ACTION to ProfileEditWorker.ACTION_UPDATE_USER_PASSWORD,
                    ProfileEditWorker.USER_EMAIL_KEY to userEmail,
                    ProfileEditWorker.USER_OLD_PASSWORD to oldPassword,
                    ProfileEditWorker.USER_NEW_PASSWORD to newPassword
                ))
                .build()

            WorkManager.getInstance(context).enqueueUniqueWork(
                ProfileEditWorker.EDIT_PASSWORD_WORK,
                ExistingWorkPolicy.APPEND,
                updateWork).result
        }
    }
}