package omega.isaacbenito.saberapp.server

import io.reactivex.Observable
import okhttp3.Response
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.util.*
import javax.xml.transform.Result

interface ServerService {

    @POST("/login")
    fun loginUser(@Body userCredentials: UserCredentials) : Call<Unit>
}