package omega.isaacbenito.saberapp.authentication

import omega.isaacbenito.saberapp.api.SaberAppServer
import omega.isaacbenito.saberapp.entities.UserCredentials
import omega.isaacbenito.saberapp.entities.UserDto
import retrofit2.Response
import javax.inject.Inject

class SaberAppServerAuthenticate @Inject constructor():
    ServerAuthenticate {

    @Inject lateinit var server: SaberAppServer

    var token : String? = null

    override suspend fun logInUser(userCredentials: UserCredentials) : Response<Unit> {
        return server.service.loginUser(userCredentials)
    }

    override suspend fun registerUser(userDto: UserDto) : Response<Unit> {
        return server.service.registerUser(userDto)
    }
}
