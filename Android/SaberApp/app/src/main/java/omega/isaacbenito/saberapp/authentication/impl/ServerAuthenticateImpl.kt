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

package omega.isaacbenito.saberapp.authentication.impl

import omega.isaacbenito.saberapp.authentication.ServerAuthenticate
import omega.isaacbenito.saberapp.data.entities.User
import omega.isaacbenito.saberapp.data.remote.server.SaberAppServer
import retrofit2.Response
import javax.inject.Inject

class ServerAuthenticateImpl @Inject constructor():
    ServerAuthenticate {

    @Inject lateinit var server: SaberAppServer

    override suspend fun logInUser(userCredentials: User.AuthCredentials) : Response<Unit> {
        return server.apiService.loginUser(userCredentials)
    }

    override suspend fun registerUser(userDto: User.Dto) : Response<Unit> {
        return server.apiService.registerUser(userDto)
    }

    override suspend fun unregisterUser(userMail: String): Response<Unit> {
        return server.apiService.unregisterUser(userMail)
    }

    override fun setAuthToken(authToken: String) {
        server.setAuthToken(authToken)
    }
}
