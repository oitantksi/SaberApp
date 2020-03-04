package omega.isaacbenito.saberapp.authentication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import omega.isaacbenito.saberapp.server.ServerService
import omega.isaacbenito.saberapp.server.model.UserCredentials
import omega.isaacbenito.saberapp.storage.database.entities.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.Future
import javax.inject.Inject

class SaberAppServerAuthentication @Inject constructor(): ServerAuthenticate {

    val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    val httpClientBuilder = OkHttpClient.Builder().addInterceptor(loggingInterceptor)

    val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClientBuilder.build())
        .baseUrl("http://10.0.2.2:8080").build()

    val authenticatorService = retrofit.create(ServerService::class.java)

    var token : String? = null

    override fun logInUser(userCredentials: UserCredentials) : Call<Unit> {
        return authenticatorService.loginUser(userCredentials)
    }

    override fun registerUser(
        user_name: String,
        user_surname: String,
        user_nickname: String,
        email: String,
        password: String,
        centre: String,
        rol:Char) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

class LoginException() : Exception() {}