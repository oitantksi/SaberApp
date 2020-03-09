package omega.isaacbenito.saberapp

import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import omega.isaacbenito.saberapp.api.ApiServerService
import omega.isaacbenito.saberapp.entities.UserCredentials
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.ConnectException
import java.util.concurrent.CountDownLatch

class ServerConnectionTest {

    lateinit var server : ApiServerService
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)

        val SERVER_URL = "http://10.0.2.2:8080"

        val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val httpClientBuilder = OkHttpClient.Builder().addInterceptor(loggingInterceptor)

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClientBuilder.build())
            .baseUrl(SERVER_URL).build()

        server = retrofit.create(ApiServerService::class.java)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    fun printLog(message: String) {
        System.out.println(message)
    }

    @Test
    fun testFetchUser() {
        runBlockingTest {
            this.launch {
                try {
                    var response = server.loginUser(UserCredentials("ramon@omega.com", "omega"))
                    println("hola")
                } catch (e: ConnectException) {
                    println("hola2")
                }
                println("hola3")
            }
            println("hola4")}
        println("adeu")
    }
}