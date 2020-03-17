package omega.isaacbenito.saberapp.authentication.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import omega.isaacbenito.saberapp.authentication.model.LoginViewModel
import omega.isaacbenito.saberapp.authentication.model.RegCentreViewModel
import omega.isaacbenito.saberapp.authentication.model.RegDataViewModel
import omega.isaacbenito.saberapp.authentication.model.RegisterViewModel
import omega.isaacbenito.saberapp.di.ViewModelKey

@Module
abstract class AuthModule {

    @SuppressWarnings("unused")
    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(viewModel: LoginViewModel): ViewModel

    @SuppressWarnings("unused")
    @Binds
    @IntoMap
    @ViewModelKey(RegDataViewModel::class)
    abstract fun bindRegDataViewModel(viewModel: RegDataViewModel): ViewModel

    @SuppressWarnings("unused")
    @Binds
    @IntoMap
    @ViewModelKey(RegCentreViewModel::class)
    abstract fun bindRegCentreViewModel(viewModel: RegCentreViewModel): ViewModel

    @SuppressWarnings("unused")
    @Binds
    @IntoMap
    @ViewModelKey(RegisterViewModel::class)
    abstract fun bindRegisterViewModel(viewModel: RegisterViewModel): ViewModel

}