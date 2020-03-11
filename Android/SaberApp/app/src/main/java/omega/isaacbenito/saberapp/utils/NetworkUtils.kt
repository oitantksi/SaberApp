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

package omega.isaacbenito.saberapp.utils

import android.content.Context
import android.net.*
import android.net.ConnectivityManager.NetworkCallback
import android.os.Build
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import java.net.InetAddress
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class NetworkUtils @Inject constructor(context: Context) {

    private lateinit var cm: ConnectivityManager

    private val _isNetworkConnected = MutableLiveData<Boolean>()

    init {
        _isNetworkConnected.value = true

        cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cm.requestNetwork(
                NetworkRequest.Builder().addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).build(),
                object : NetworkCallback() {
                    override fun onAvailable(network: Network?) {
                        super.onAvailable(network)
                        _isNetworkConnected.postValue(true)
                    }

                    override fun onLost(network: Network) {
                        super.onLost(network)
                        _isNetworkConnected.postValue(false)
                    }

                    override fun onUnavailable() {
                        super.onUnavailable()
                        _isNetworkConnected.postValue(false)
                    }
                }, 1000)
        }
    }

    var isConnected: Boolean = checkconnection()

    private fun checkconnection() : Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
            _isNetworkConnected.value = activeNetwork?.isConnectedOrConnecting == true
        }
        return _isNetworkConnected.value!!
    }





}