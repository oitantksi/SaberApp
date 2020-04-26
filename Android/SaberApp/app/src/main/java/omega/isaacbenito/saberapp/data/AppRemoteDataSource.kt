package omega.isaacbenito.saberapp.data

import androidx.lifecycle.LiveData
import omega.isaacbenito.saberapp.data.Result.Error
import omega.isaacbenito.saberapp.data.Result.Success
import omega.isaacbenito.saberapp.data.entities.*

interface AppRemoteDataSource {

    /**
     * Obté un [List] de [Centre] de l'origen de dades remot de l'aplicació
     *
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
    suspend fun getUser(userMail: String): Result<User>

    /**
     * Actualitza el [User] a l'origen de dades remot de l'aplicació
     *
     * @param userEmail correu electrònic identificador de l'usuari a actualitzar
     * @param user objecte [User] a desar
     * @return [Result] amb el resultat de l'operació.
     *  Si [Result] és [Success] encapsula un objecte null
     *  Si [Result] és [Error] encapsula l' [Exception] llançada
     */
    suspend fun updateUser(userEmail: String, user: User): Result<Unit?>

    /**
     * Actualitza la contrassenya a l'origen de dades remot de l'aplicació
     *
     * @param userEmail correu electrònic identificador de l'usuari a actualitzar
     * @param oldPassword contrassenya actual de l'usuari
     * @param newPassword contrassenya nova de l'usuari
     * @return [Result] amb el resultat de l'operació.
     *  Si [Result] és [Success] encapsula un objecte null
     *  Si [Result] és [Error] encapsula l' [Exception] llançada
     */
    suspend fun updatePassword(userEmail: String, oldPassword: String, newPassword: String): Result<Unit?>

    suspend fun getMateries() : Result<List<Materia>>

    suspend fun getPreguntes() : Result<List<Pregunta.ServerQuest>>

    suspend fun getRespostes(userId: Long) : Result<List<Resposta>>

    suspend fun setResposta(resposta: Resposta) : Result<Nothing?>

}