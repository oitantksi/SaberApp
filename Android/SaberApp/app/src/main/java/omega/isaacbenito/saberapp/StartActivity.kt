package omega.isaacbenito.saberapp

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.net.NetworkRequest
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import omega.isaacbenito.saberapp.authentication.ui.AuthActivity
import omega.isaacbenito.saberapp.server.ServerService
import omega.isaacbenito.saberapp.server.model.UserCredentials
import omega.isaacbenito.saberapp.server.model.UserDto
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Runnable
import java.net.SocketTimeoutException

class StartActivity : AppCompatActivity() {

    val TAG = this.javaClass.name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(this.javaClass.name, "launched")

        setContentView(R.layout.activity_start)

        supportActionBar?.hide()

        Handler().postDelayed(Runnable {
            startActivity(Intent(this, AuthActivity::class.java))
        }, 2000L)
    }

//    fun isConnected(): Boolean {
//        if (Build.VERSION.SDK_INT >= 29) {
//            return (application as SaberApp).hasInternetConnection.value!!
//        } else {
//            val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//            val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
//            return activeNetwork?.isConnected == true
//        }
//    }

    fun testRetrofit() {

        val server = startConnection()

        var viewModelJob = Job()
        val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )

        coroutineScope.launch { loginTest(server) }


    }

    fun startConnection() : ServerService {
        val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS)
        val httpClientBuilder = OkHttpClient.Builder().addInterceptor(loggingInterceptor)

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClientBuilder.build())
            .baseUrl("http://10.0.2.2:8080").build()

        return retrofit.create(ServerService::class.java)
    }

    suspend fun registerTest(server: ServerService) {
        val user = UserDto("Isaac",
            "Benito",
            "isaac.benito@papa.com",
            "nick",
            "password",
            "center",
            'A')

        var response = server.registerUser(user)
        Log.d(TAG, response.isSuccessful.toString())
    }

    suspend fun loginTest(server: ServerService) {
        val registered_user = UserCredentials("ramon@omega.com", "omega")
        val wrong_password = UserCredentials("ramon@omega.com", "omeg")
        val not_registered_user = UserCredentials("pepito@omega.com", "omega")
        val new_user = UserCredentials("isaac.benito@papa.com", "password")

//        if (isConnected()) {
//            try {
//                var response = server.loginUser(registered_user)
//                Log.d(TAG, response.headers().get("Authorization").toString())
//            } catch (e: SocketTimeoutException) {
//                Log.d(TAG, "Socket Timeout")
//            }
//        } else {
//            Log.d(TAG, "No internet connection")
//        }

    }
}