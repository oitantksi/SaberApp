package omega.isaacbenito.saberapp.user.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import omega.isaacbenito.saberapp.di.ViewModelKey
import omega.isaacbenito.saberapp.user.model.UserViewModel

@Module
abstract class UserModule {

    @SuppressWarnings("unused")
    @Binds
    @IntoMap
    @ViewModelKey(UserViewModel::class)
    abstract fun bindUserMainViewModel(viewModel: UserViewModel): ViewModel


}