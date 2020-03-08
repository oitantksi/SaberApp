package omega.isaacbenito.saberapp.api

import omega.isaacbenito.saberapp.entities.UserCredentials
import omega.isaacbenito.saberapp.entities.UserDto
import omega.isaacbenito.saberapp.entities.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface ApiServerService {

    @POST("/login")
    suspend fun loginUser(@Body userCredentials: UserCredentials) : Response<Unit>

    @POST("/user")
    suspend fun registerUser(@Body user: UserDto): Response<Unit>

    @GET("/user/id")
    suspend fun getUser(@Body userId: Long, authToken: String): User

    @GET("/user/email")
    suspend fun getUser(@Body userMail: String, @Body authToken: String): User

    @POST("/login")
    fun loginUserTest(@Body userCredentials: UserCredentials) : Call<Unit>

    @GET("/user/email")
    fun getUserTest(@Body userMail: String, @Body authToken: String): Call<User>

}