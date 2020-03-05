package omega.isaacbenito.saberapp.server

import omega.isaacbenito.saberapp.server.model.UserCredentials
import omega.isaacbenito.saberapp.server.model.UserDto
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface ServerService {

    @POST("/login")
    suspend fun loginUser(@Body userCredentials: UserCredentials) : Response<Unit>

    @POST("/user")
    suspend fun registerUser(@Body user: UserDto): Response<Unit>

}