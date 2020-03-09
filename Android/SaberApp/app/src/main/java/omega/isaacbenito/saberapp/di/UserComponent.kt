package omega.isaacbenito.saberapp.di

import dagger.Subcomponent
import omega.isaacbenito.saberapp.ui.MainActivity
import omega.isaacbenito.saberapp.ui.UserProfileFragment

@LoggedUserScope
@Subcomponent
interface UserComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): UserComponent
    }

    // Classes that can be injected by this Component
    fun inject(activity: MainActivity)
    fun inject(fragment: UserProfileFragment)
}