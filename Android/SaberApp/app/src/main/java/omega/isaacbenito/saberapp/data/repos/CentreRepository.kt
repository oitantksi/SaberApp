package omega.isaacbenito.saberapp.data.repos

import androidx.lifecycle.LiveData
import kotlinx.coroutines.runBlocking
import omega.isaacbenito.saberapp.data.AppLocalDataSource
import omega.isaacbenito.saberapp.data.AppRemoteDataSource
import omega.isaacbenito.saberapp.data.Result
import omega.isaacbenito.saberapp.data.Result.Error
import omega.isaacbenito.saberapp.data.Result.Success
import omega.isaacbenito.saberapp.data.di.DataModule
import omega.isaacbenito.saberapp.data.entities.Centre
import omega.isaacbenito.saberapp.utils.NetworkUtils
import java.net.ConnectException
import javax.inject.Inject

/**
 * @author Isaac Benito
 *
 * Repositori de dades del tipus [Centre]
 *
 * @property localDataSource origen de dades local
 * @property remoteDataSource origen de dades remot
 */
class CentreRepository @Inject constructor(
    @DataModule.LocalDataSource private val localDataSource: AppLocalDataSource,
    @DataModule.RemoteDataSource private val remoteDataSource: AppRemoteDataSource
) {
    @Inject
    lateinit var networkUtils: NetworkUtils

    /**
     * Obté un [List] de [Centre] de les fonts de dades de l'aplicació
     *
     * @param forceUpdate [Boolean] `true` si s'han d'actualitzar forçosament les dades
     * @return [Result] amb el resultat de l'operació.
     *  Si [Result] és [Success] encapsula un objecte non-null [LiveData] amb el resultat de la consulta
     *  Si [Result] és [Error] encapsula l' [Exception] llançada
     */
    suspend fun getCentres(forceUpdate: Boolean): Result<LiveData<List<Centre>>> {

        if (forceUpdate) {
            if (!networkUtils.isNetworkConnected) {
                return Error(ConnectException())
            }

            val remoteResult = runBlocking { return@runBlocking refreshCentres() }

            if (remoteResult is Error) {
                return Error(remoteResult.exception)
            }
        } else {
            if (networkUtils.isNetworkConnected) refreshCentres()
        }

        return localDataSource.getAllCentres()
    }

    /**
     * Actualitza l'origen de dades local amb les dades de l'origen remot
     *
     * @return [Result] amb el resultat de l'operació.
     *  Si [Result] és [Error] encapsula l' [Exception] llançada
     *
     * @throws IllegalStateException
     */
    private suspend fun refreshCentres(): Result<Unit> {
        val remoteResult = remoteDataSource.getAllCentres()

        when (remoteResult) {
            is Success -> {
                localDataSource.updateCentres(remoteResult.data)
                return Success(Unit)
            }
            is Error -> return Error(remoteResult.exception)
            else -> throw IllegalStateException()
        }
    }
}