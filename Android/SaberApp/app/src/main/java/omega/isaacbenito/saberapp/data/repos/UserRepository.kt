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

package omega.isaacbenito.saberapp.data.repos

import androidx.lifecycle.LiveData
import omega.isaacbenito.saberapp.data.Result
import omega.isaacbenito.saberapp.data.Result.Error
import omega.isaacbenito.saberapp.data.Result.Success
import omega.isaacbenito.saberapp.data.entities.User


/**
 * @author Isaac Benito
 *
 * Repositori de dades del tipus [User]
 *
 * @property localDataSource origen de dades local
 * @property remoteDataSource origen de dades remot
 */
interface UserRepository {

    /**
     * Obté un objecte [User] de les fonts de dades de l'aplicació
     *
     * @param userMail correu electrònic de l'usuari
     * @return [Result] amb el resultat de l'operació.
     *  Si [Result] és [Success] encapsula un objecte non-null [User]
     *  Si [Result] és [Error] encapsula l' [Exception] llançada
     */
    suspend fun getUser(userMail: String, forceUpdate: Boolean): Result<LiveData<User>>

    suspend fun getUser(userId: Long): Result<User>

    suspend fun getUserId(userMail: String): Result<Long>

    suspend fun updateUser(userEmail: String, user: User)

    suspend fun updateUserPassword(userEmail: String, oldPassword: String, newPassword: String)
}