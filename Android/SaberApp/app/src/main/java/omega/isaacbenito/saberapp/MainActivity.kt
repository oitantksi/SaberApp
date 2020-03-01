package omega.isaacbenito.saberapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import omega.isaacbenito.saberapp.databinding.ActivityMainBinding
import omega.isaacbenito.saberapp.server.ServerService
import omega.isaacbenito.saberapp.server.UserCredentials
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var drawerLayout : DrawerLayout
    private lateinit var appBarConfiguration : AppBarConfiguration

    private val TAG = this.javaClass.name.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "launched")

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        drawerLayout = binding.drawerLayout

        val navController = this.findNavController(R.id.mainNavHostFragment)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)

        navController.addOnDestinationChangedListener {
                nc: NavController, nd: NavDestination, _: Bundle? ->
            if (nd.id == nc.graph.startDestination) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            } else {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
        }

        NavigationUI.setupWithNavController(binding.navView, navController)

        testingRetrofit()

    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.mainNavHostFragment)
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }

    fun testingRetrofit() {
        val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val httpClientBuilder = OkHttpClient.Builder().addInterceptor(loggingInterceptor)

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClientBuilder.build())
            .baseUrl("http://10.0.2.2:8080").build()

        val severService = retrofit.create(ServerService::class.java)

        val user = UserCredentials("ramon@omega.com", "omega")
        sendLogin(severService, user)

    }

    fun sendLogin(serverService: ServerService, userCredentials: UserCredentials){
        serverService.loginUser(userCredentials).enqueue(
            object : Callback<Unit> {
                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    if(response.isSuccessful) {
                        finishLogin(response.headers().get("Authorization"))
                    } else {
                        Log.d("SEND LOGIN SUCCESS", response.message())
                    }
                }

                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    Log.d("SEND LOGIN FAILURE", t.message)
                }
        })
    }

    fun finishLogin(token: String?) {
        Log.d("LOGIN TOKEN", token)
    }

}
