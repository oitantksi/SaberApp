package omega.isaacbenito.saberapp.user.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import omega.isaacbenito.saberapp.di.viewModel.ViewModelKey
import omega.isaacbenito.saberapp.user.model.UserViewModel
import omega.isaacbenito.saberapp.user.ui.UserMainFragment
import omega.isaacbenito.saberapp.user.ui.UserProfileFragment

@Module
abstract class UserModule {

    @SuppressWarnings("unused")
    @Binds
    @IntoMap
    @ViewModelKey(UserViewModel::class)
    abstract fun bindUserViewModel(viewModel: UserViewModel): ViewModel

    @ContributesAndroidInjector()
    internal abstract fun userMainFragment() : UserMainFragment

    @ContributesAndroidInjector()
    internal abstract fun userProfileFragment() : UserProfileFragment
}