/*
 * This file is part of SaberApp.
 *
 *     SaberApp is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     SaberApp is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with SaberApp.  If not, see <https://www.gnu.org/licenses/>.
 */

package omega.isaacbenito.saberapp.data.remote.server

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Isaac Benito
 *
 * Classe que genera i manté la instància de ocmunicació amb el servidor.
 *
 * Aquesta classe s'instancia una unica vegada i es treballa amb la mateixa
 * instància en tota la aplicació.
 */
@Singleton
class SaberAppServer @Inject constructor() {

    companion object {
        const val API_SERVER_URL = "http://10.0.2.2:8080"
    }

    // Logging interceptor per a mostrar en el log de l'aplicació la comunicació amb el servidor
    // durant el desenvolupament. Cal treure'l en la varsió final.
    private val loggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    lateinit var apiService: ApiServerService
        private set

    private var authToken: String? = null

    init {
        buildRetrofit()
    }

    /**
     * Rep el token d'autorització de l'API i crea un interceptor que intercepta cada crida al
     * servidor i afegeix el token als hedaers de la crida.
     *
     * @param authToken: String - token d'autorització de l'API
     */
    fun setAuthToken(authToken: String) {
        this.authToken = authToken

        buildRetrofit()
    }

    fun hasAuthToken(): Boolean {
        return authToken != null
    }

    /**
     * Construeix una instància de Retrofit i genera una implementació de
     * la interfície ApiServerInstance
     */
    private fun buildRetrofit() {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(provideHttpClient())
            .baseUrl(API_SERVER_URL).build()

        apiService = retrofit.create(ApiServerService::class.java)
    }

    private fun provideHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder().let {
            it.connectTimeout(30, TimeUnit.SECONDS)
            it.readTimeout(30, TimeUnit.SECONDS)
            it.writeTimeout(30, TimeUnit.SECONDS)
            if (authToken != null) {
                it.addInterceptor { chain ->
                    chain.proceed(
                        chain.request().newBuilder().addHeader("Authorization", authToken!!).build()
                    )
                }
            }
            it.addInterceptor(loggingInterceptor)
        }

        return builder.build()
    }
}