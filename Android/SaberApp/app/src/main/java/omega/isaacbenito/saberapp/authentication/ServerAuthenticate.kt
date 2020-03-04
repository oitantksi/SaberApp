package omega.isaacbenito.saberapp.authentication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import omega.isaacbenito.saberapp.server.model.UserCredentials
import retrofit2.Call
import java.util.concurrent.Future

interface ServerAuthenticate {

    fun logInUser(userCredentials: UserCredentials) : Call<Unit>

    fun registerUser(
        user_name: String,
        user_surname: String,
        user_nickname: String,
        email: String,
        password: String,
        centre: String,
        curs: String,
        aula: String)
}