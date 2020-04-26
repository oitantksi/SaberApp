package omega.isaacbenito.saberapp.data.repos

import androidx.lifecycle.LiveData
import omega.isaacbenito.saberapp.data.Result
import omega.isaacbenito.saberapp.data.Result.Error
import omega.isaacbenito.saberapp.data.Result.Success
import omega.isaacbenito.saberapp.data.entities.Centre

/**
 * @author Isaac Benito
 *
 * Repositori de dades del tipus [Centre]
 *
 * @property localDataSource origen de dades local
 * @property remoteDataSource origen de dades remot
 */
interface CentreRepository {

    /**
     * Obté un [List] de [Centre] de les fonts de dades de l'aplicació
     *
     * @param forceUpdate [Boolean] `true` si s'han d'actualitzar forçosament les dades
     * @return [Result] amb el resultat de l'operació.
     *  Si [Result] és [Success] encapsula un objecte non-null [LiveData] amb el resultat de la consulta
     *  Si [Result] és [Error] encapsula l' [Exception] llançada
     */
    suspend fun getCentres(forceUpdate: Boolean): Result<LiveData<List<Centre>>>

}