package omega.isaacbenito.saberapp.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SaberAppServer @Inject constructor(){

    private val SERVER_URL = "http://10.0.2.2:8080"

    private val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val httpClientBuilder = OkHttpClient.Builder().addInterceptor(loggingInterceptor)

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClientBuilder.build())
        .baseUrl(SERVER_URL).build()

    val service = retrofit.create(ApiServerService::class.java)
}