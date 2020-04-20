//package omega.isaacbenito.saberapp.di
//
//import android.content.Context
//import dagger.BindsInstance
//import dagger.Component
//import dagger.android.support.AndroidSupportInjectionModule
//import omega.isaacbenito.saberapp.authentication.di.AuthModule
//import omega.isaacbenito.saberapp.data.di.DataModule
//import omega.isaacbenito.saberapp.di.viewModel.ViewModelFactoryModule
//import omega.isaacbenito.saberapp.user.di.UserModule
//import javax.inject.Singleton
//
//@Singleton
//@Component(
//    modules = [
//        AndroidSupportInjectionModule::class,
//        AppModule::class,
//        ViewModelFactoryModule::class,
////        WorkerFactoryModule::class,
//        DataModule::class,
//        AuthModule::class,
//        UserModule::class
//    ]
//)
//interface TestAppComponent : AppComponent {
//
//    @Component.Factory
//    interface Factory {
//        // With @BindsInstance, the Context passed in will be available in the graph
//        val appComponent = AppComponent.Factory.create()
//        fun create(@BindsInstance context: Context): AppComponent
//    }
//
//}
//
