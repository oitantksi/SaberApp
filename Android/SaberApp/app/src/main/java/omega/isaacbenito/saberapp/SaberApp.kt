package omega.isaacbenito.saberapp

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import omega.isaacbenito.saberapp.di.AppComponent
import omega.isaacbenito.saberapp.di.DaggerAppComponent

open class SaberApp : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }

    private lateinit var networkStatus: ConnectivityManager.NetworkCallback

    private val _hasInternetconnection = MutableLiveData<Boolean>()
    val hasInternetConnection : LiveData<Boolean>
        get() = _hasInternetconnection

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            networkStatus = NetworkStatus(_hasInternetconnection)
            val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            cm.requestNetwork(
                NetworkRequest.Builder().addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).build(),
                networkStatus)

        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    class NetworkStatus(private val _hasInternetconnection : MutableLiveData<Boolean>) : ConnectivityManager.NetworkCallback() {

        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            _hasInternetconnection.value = true
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            _hasInternetconnection.value = false
        }
    }
}