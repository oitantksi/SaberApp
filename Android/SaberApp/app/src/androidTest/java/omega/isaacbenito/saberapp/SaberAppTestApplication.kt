package omega.isaacbenito.saberapp

import omega.isaacbenito.saberapp.di.AppComponent
import omega.isaacbenito.saberapp.di.DaggerTestAppComponent

class SaberAppTestApplication : SaberApp() {

    override fun initializeComponent(): AppComponent {
        // Creates a new TestAppComponent that injects fakes types
        return DaggerTestAppComponent.factory().create(applicationContext)
    }
}