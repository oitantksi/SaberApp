package omega.isaacbenito.saberapp.di

import androidx.recyclerview.widget.RecyclerView
import dagger.Subcomponent
import omega.isaacbenito.saberapp.authentication.ui.AuthActivity
import omega.isaacbenito.saberapp.authentication.ui.LoginFragment
import omega.isaacbenito.saberapp.authentication.ui.RegCentreFragment
import omega.isaacbenito.saberapp.authentication.ui.RegDataFragment

@ActivityScope
@Subcomponent
interface AuthComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): AuthComponent
    }

    // Expose activities and fragments
    fun inject(activity: AuthActivity)
    fun inject(fragment: LoginFragment)
    fun inject(fragment: RegDataFragment)
    fun inject(fragment: RegCentreFragment)

}