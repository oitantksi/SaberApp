package omega.isaacbenito.saberapp.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import omega.isaacbenito.saberapp.authentication.registration.RegistrationActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppSubComponents::class,
    ServerAuthenticateModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    // Expose SubComponents
    fun registrationComponent(): RegistrationComponent.Factory
    fun loginComponent(): LoginComponent.Factory
}