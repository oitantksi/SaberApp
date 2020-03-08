package omega.isaacbenito.saberapp.authentication

import omega.isaacbenito.saberapp.entities.UserCredentials
import omega.isaacbenito.saberapp.entities.UserDto
import retrofit2.Response

interface ServerAuthenticate {

    suspend fun logInUser(userCredentials: UserCredentials) : Response<Unit>

    suspend fun registerUser(userDto: UserDto) : Response<Unit>
}