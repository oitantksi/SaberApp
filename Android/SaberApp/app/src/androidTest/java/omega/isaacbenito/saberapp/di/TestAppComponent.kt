package omega.isaacbenito.saberapp.di

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        DaggerViewModelFactoryModule::class,
        AppSubComponentsModule::class]
)
interface TestAppComponent : AppComponent {

    @Component.Factory
    interface Factory {
        // With @BindsInstance, the Context passed in will be available in the graph
//        fun create(@BindsInstance context: Context): TestAppComponent
    }
}