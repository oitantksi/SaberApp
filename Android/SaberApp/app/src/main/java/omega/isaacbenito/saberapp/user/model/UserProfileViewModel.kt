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

package omega.isaacbenito.saberapp.user.model

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import omega.isaacbenito.saberapp.authentication.AuthenticationManager
import omega.isaacbenito.saberapp.authentication.ui.AuthState
import omega.isaacbenito.saberapp.di.LoggedUserScope
import omega.isaacbenito.saberapp.user.User
import omega.isaacbenito.saberapp.user.UserRepository
import javax.inject.Inject

@LoggedUserScope
class UserProfileViewModel @Inject constructor(
    userRepository: UserRepository
) : Fragment() {

    @Inject
    lateinit var authManager: AuthenticationManager

    val user: LiveData<User> = userRepository.getUser()

    fun unregisterUser(): LiveData<AuthState> {
        return authManager.unregisterUser()
    }

    fun logoutUser(): LiveData<AuthState> {
        return authManager.logoutUser()
    }
}