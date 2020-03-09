package omega.isaacbenito.saberapp.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import omega.isaacbenito.saberapp.ui.StartActivity
import omega.isaacbenito.saberapp.authentication.AuthenticationManager
import omega.isaacbenito.saberapp.entities.User
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppSubComponents::class,
    ServerAuthenticateModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        // With @BindsInstance, the Context passed in will be available in the graph
        fun create(@BindsInstance context: Context): AppComponent
    }

    // Expose SubComponents
    fun authComponent() : AuthComponent.Factory
    fun authManager() : AuthenticationManager

    fun userComponent() : UserComponent.Factory

    fun inject(activity: StartActivity)

}