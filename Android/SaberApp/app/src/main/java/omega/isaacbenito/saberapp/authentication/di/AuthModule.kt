package omega.isaacbenito.saberapp.authentication.di

import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import omega.isaacbenito.saberapp.authentication.AuthenticationManager
import omega.isaacbenito.saberapp.authentication.ServerAuthenticate
import omega.isaacbenito.saberapp.authentication.impl.AuthenticationManagerImpl
import omega.isaacbenito.saberapp.authentication.impl.ServerAuthenticateImpl
import omega.isaacbenito.saberapp.authentication.ui.AuthActivity

/**
 * Mòdul que afegeix els models de les vistes del component d'autenticació en el gràfic de l'aplicació
 *
 * @author Isaac Benito
 */
@Module(subcomponents = [AuthComponent::class])
abstract class AuthModule {

    @Binds
    @IntoMap
    @ClassKey(AuthActivity::class)
    abstract fun bindYourAndroidInjectorFactory(factory: AuthComponent.Factory?): AndroidInjector.Factory<*>?

    @Binds
    abstract fun authManagerBind(authManager: AuthenticationManagerImpl) : AuthenticationManager

    @Binds
    abstract fun provideServerAuthenticate(server: ServerAuthenticateImpl): ServerAuthenticate

}