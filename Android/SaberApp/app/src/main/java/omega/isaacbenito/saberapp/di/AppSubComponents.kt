package omega.isaacbenito.saberapp.di

import dagger.Module

@Module(subcomponents = [
    RegistrationComponent::class,
    LoginComponent::class])
class AppSubComponents