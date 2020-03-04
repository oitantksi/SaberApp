package omega.isaacbenito.saberapp

import android.app.Application
import omega.isaacbenito.saberapp.di.AppComponent
import omega.isaacbenito.saberapp.di.DaggerAppComponent

open class SaberApp : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }
}