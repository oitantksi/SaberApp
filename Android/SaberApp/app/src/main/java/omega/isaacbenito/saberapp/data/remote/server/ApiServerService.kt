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

package omega.isaacbenito.saberapp.data.remote.server

import omega.isaacbenito.saberapp.data.entities.*
import retrofit2.Response
import retrofit2.http.*

/**
 * @author Isaac Benito
 *
 * Interfície que defineix les diferents crides posibles a l'API
 */
interface ApiServerService {

    @POST("/login")
    suspend fun loginUser(@Body userCredentials: User.AuthCredentials): Response<Unit>

    @POST("/user")
    suspend fun registerUser(@Body user: User.Dto): Response<Unit>

    @DELETE("/user/{email}")
    suspend fun unregisterUser(@Path("email") userMail: String): Response<Unit>

    @GET("/user/email/{email}")
    suspend fun getUser(@Path("email") userMail: String): Response<User>

    @PUT("/user/{email}")
    suspend fun updateUser(@Path("email") userMail: String, @Body user: User): Response<Unit>

    @PUT("/user/password/{email}")
    suspend fun updatePassword(@Path("email") userMail: String, @Body passwordDto: User.UpdatePasswordDto): Response<Unit>

    @GET("/centres")
    suspend fun getCentres(): Response<List<Centre>>

    @GET("/materies")
    suspend fun getMateries() : Response<List<Materia>>

    @GET("/preguntes/{materia}")
    suspend fun getPreguntesByMateria(@Path("materia") materia: String) : Response<List<Pregunta.ServerQuest>>

    @GET("/preguntes/totes")
    suspend fun getPreguntes() : Response<List<Pregunta.ServerQuest>>

    @GET("/respostes/alumno/{idAlumno}")
    suspend fun getRespostesAlumne(@Path("idAlumno") idAlumne: Long) : Response<List<Resposta>>

    @POST("/respostes/alumno")
    suspend fun postResposta(@Body resposta: Resposta.Dto) : Response<Unit>
}