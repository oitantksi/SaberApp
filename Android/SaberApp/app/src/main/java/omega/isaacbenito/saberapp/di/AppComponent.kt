package omega.isaacbenito.saberapp.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
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
    fun authComponent() : AuthComponent.Factory
}