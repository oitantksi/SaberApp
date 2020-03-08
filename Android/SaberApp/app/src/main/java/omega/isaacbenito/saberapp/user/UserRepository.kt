package omega.isaacbenito.saberapp.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import omega.isaacbenito.saberapp.api.SaberAppServer
import omega.isaacbenito.saberapp.authentication.AuthenticationManager
import omega.isaacbenito.saberapp.di.LoggedUserScope
import omega.isaacbenito.saberapp.entities.User
import javax.inject.Inject

@LoggedUserScope
class UserRepository @Inject constructor() {

    @Inject lateinit var server: SaberAppServer

    @Inject lateinit var authManager: AuthenticationManager

    var userRepoJob = Job()
    val userRepoScope =  CoroutineScope(userRepoJob + Dispatchers.Main)

    fun getUser() : LiveData<User> {
        val user = MutableLiveData<User>()
        userRepoScope.launch {
            user.value = server.service.getUser(authManager.userMail, authManager.getAuthToken())
        }
        return user
    }
}