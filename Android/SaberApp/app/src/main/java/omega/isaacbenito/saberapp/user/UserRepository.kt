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
            user.value = server.service.getUser(authManager.userMail)
        }
        return user
    }
}