package omega.isaacbenito.saberapp.di

import dagger.Binds
import dagger.Module
import omega.isaacbenito.saberapp.authentication.SaberAppServerAuthentication
import omega.isaacbenito.saberapp.authentication.ServerAuthenticate

@Module
abstract class ServerAuthenticateModule {

    @Binds
    abstract fun provideServerAuthenticate(server: SaberAppServerAuthentication) : ServerAuthenticate
}