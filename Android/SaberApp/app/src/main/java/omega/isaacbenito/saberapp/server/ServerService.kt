package omega.isaacbenito.saberapp.server

import omega.isaacbenito.saberapp.server.model.UserCredentials
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ServerService {

    @POST("/login")
    fun loginUser(@Body userCredentials: UserCredentials) : Call<Unit>

}