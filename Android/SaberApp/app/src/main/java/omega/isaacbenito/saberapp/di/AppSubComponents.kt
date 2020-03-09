package omega.isaacbenito.saberapp.di

import dagger.Module

@Module(subcomponents = [
    AuthComponent::class,
    UserComponent::class])
class AppSubComponents