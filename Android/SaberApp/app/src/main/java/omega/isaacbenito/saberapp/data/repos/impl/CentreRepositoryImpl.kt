package omega.isaacbenito.saberapp.data.repos.impl

import androidx.lifecycle.LiveData
import omega.isaacbenito.saberapp.data.AppLocalDataSource
import omega.isaacbenito.saberapp.data.AppRemoteDataSource
import omega.isaacbenito.saberapp.data.Result
import omega.isaacbenito.saberapp.data.Result.Error
import omega.isaacbenito.saberapp.data.Result.Success
import omega.isaacbenito.saberapp.data.di.DataModule
import omega.isaacbenito.saberapp.data.entities.Centre
import omega.isaacbenito.saberapp.data.repos.CentreRepository
import omega.isaacbenito.saberapp.utils.NetworkUtils
import timber.log.Timber
import javax.inject.Inject

/**
 * @author Isaac Benito
 *
 * Repositori de dades del tipus [Centre]
 *
 * @property localDataSource origen de dades local
 * @property remoteDataSource origen de dades remot
 */
class CentreRepositoryImpl @Inject constructor(
    @DataModule.LocalDataSource private val localDataSource: AppLocalDataSource,
    @DataModule.RemoteDataSource private val remoteDataSource: AppRemoteDataSource
) : CentreRepository {
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
    override suspend fun getCentres(forceUpdate: Boolean): Result<LiveData<List<Centre>>> {

        refreshCentres()

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
    private suspend fun refreshCentres() {
        when (val remoteResult = remoteDataSource.getAllCentres()) {
            is Success -> {
                localDataSource.updateCentres(remoteResult.data)
            }
            is Error -> Timber.w(
                "No s'ha pogut recuperar el llistat de centres del servidor:\n${remoteResult.exception}")
            else -> throw IllegalStateException()
        }
    }
}