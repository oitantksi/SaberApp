package omega.isaacbenito.saberapp.di

import dagger.Subcomponent
import omega.isaacbenito.saberapp.authentication.login.LoginActivity

@ActivityScope
@Subcomponent
interface LoginComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): LoginComponent
    }

    fun inject(activity: LoginActivity)
}