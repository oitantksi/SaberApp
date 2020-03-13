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

package omega.isaacbenito.saberapp.api

import omega.isaacbenito.saberapp.api.entities.Centre
import omega.isaacbenito.saberapp.api.entities.UserCredentials
import omega.isaacbenito.saberapp.api.entities.UserDto
import omega.isaacbenito.saberapp.user.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * @author Isaac Benito
 *
 * Interfície que defineix les diferents crides posibles a l'API
 */
interface ApiServerService {

    @POST("/login")
    suspend fun loginUser(@Body userCredentials: UserCredentials): Response<Unit>

    @POST("/user")
    suspend fun registerUser(@Body user: UserDto): Response<Unit>

    @GET("/user/email/{email}")
    suspend fun getUser(@Path("email") userMail: String): User

    @GET("/centres")
    suspend fun getCentres(): List<Centre>

}