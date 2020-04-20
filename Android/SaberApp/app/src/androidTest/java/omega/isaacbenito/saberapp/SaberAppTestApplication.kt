//package omega.isaacbenito.saberapp
//
//import androidx.work.Configuration
//import androidx.work.WorkManager
//import dagger.android.AndroidInjector
//import dagger.android.DaggerApplication
//import dagger.android.DispatchingAndroidInjector
//import dagger.android.HasAndroidInjector
//import omega.isaacbenito.saberapp.di.AppComponent
//import omega.isaacbenito.saberapp.di.DaggerAppComponent
//import omega.isaacbenito.saberapp.di.DaggerTestAppComponent
//import javax.inject.Inject
//
////import omega.isaacbenito.saberapp.di.DaggerTestAppComponent
//
//class SaberAppTestApplication : SaberApp(), HasAndroidInjector {
//
//    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
//        return DaggerTestAppComponent.factory().create(applicationContext)
//    }
//
//    override fun onCreate() {
//        super.onCreate()
//        // register ours custom factory to WorkerManager
//        WorkManager.initialize(this, Configuration.Builder().setWorkerFactory(workerFactory).build())
//    }
//}