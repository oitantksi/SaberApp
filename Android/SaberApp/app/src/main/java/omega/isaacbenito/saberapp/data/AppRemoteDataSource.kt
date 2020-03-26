package omega.isaacbenito.saberapp.data

import androidx.lifecycle.LiveData
import omega.isaacbenito.saberapp.data.Result.Error
import omega.isaacbenito.saberapp.data.Result.Success
import omega.isaacbenito.saberapp.data.entities.Centre
import omega.isaacbenito.saberapp.data.entities.User

interface AppRemoteDataSource {

    /**
     * Obté un [List] de [Centre] de l'origen de dades remot de l'aplicació
     *
     * @param forceUpdate [Boolean] `true` si s'han d'actualitzar forçosament les dades
     * @return [Result] amb el resultat de l'operació.
     *  Si [Result] és [Success] encapsula un objecte non-null [LiveData] amb el resultat de la consulta
     *  Si [Result] és [Error] encapsula l' [Exception] llançada
     */
    suspend fun getAllCentres(): Result<List<Centre>>

    /**
     * Obté un [User] de l'origen de dades remot de l'aplicació
     *
     * @param userMail correu electrònic de l'usuari
     * @return [Result] amb el resultat de l'operació.
     *  Si [Result] és [Success] encapsula un objecte non-null [User]
     *  Si [Result] és [Error] encapsula l' [Exception] llançada
     */
    suspend fun getUser(email: String): Result<User>

    /**
     * Desa un [User] a l'origen de dades remot de l'aplicació
     *
     * @param user objecte [User] a desar
     * @return [Result] amb el resultat de l'operació.
     *  Si [Result] és [Success] encapsula un objecte non-null [User]
     *  Si [Result] és [Error] encapsula l' [Exception] llançada
     */
    suspend fun updateUser(user: User): Result<Unit?>
}