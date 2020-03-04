package omega.isaacbenito.saberapp.api

import omega.isaacbenito.saberapp.storage.database.entities.User
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("user")
    fun getUser(): Call<User>
}