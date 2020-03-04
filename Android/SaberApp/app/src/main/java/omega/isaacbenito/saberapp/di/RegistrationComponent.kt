package omega.isaacbenito.saberapp.di

import dagger.Subcomponent
import omega.isaacbenito.saberapp.authentication.registration.RegCentreFragment
import omega.isaacbenito.saberapp.authentication.registration.RegDataFragment
import omega.isaacbenito.saberapp.authentication.registration.RegistrationActivity

@ActivityScope
@Subcomponent
interface RegistrationComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): RegistrationComponent
    }

    // Expose activities and fragments
    fun inject(activity: RegistrationActivity)
    fun inject(fragment: RegDataFragment)
    fun inject(fragment: RegCentreFragment)
}