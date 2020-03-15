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

package omega.isaacbenito.saberapp.authentication

import omega.isaacbenito.saberapp.api.entities.UserCredentials
import omega.isaacbenito.saberapp.api.entities.UserDto
import retrofit2.Response

/**
 * Interficie que defineix les interaccions a implementar entre el mòdul d'autenticació
 * i el servidor.
 */
interface ServerAuthenticate {

    suspend fun logInUser(userCredentials: UserCredentials) : Response<Unit>

    suspend fun registerUser(userDto: UserDto) : Response<Unit>

    suspend fun unregisterUser(userMail: String): Response<Unit>

    fun setAuthToken(authToken: String)
}