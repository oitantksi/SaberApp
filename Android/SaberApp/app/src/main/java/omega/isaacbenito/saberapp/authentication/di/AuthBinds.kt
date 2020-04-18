package omega.isaacbenito.saberapp.authentication.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import omega.isaacbenito.saberapp.authentication.impl.AuthWorker
import omega.isaacbenito.saberapp.authentication.model.LoginViewModel
import omega.isaacbenito.saberapp.authentication.model.RegCentreViewModel
import omega.isaacbenito.saberapp.authentication.model.RegDataViewModel
import omega.isaacbenito.saberapp.authentication.model.RegisterViewModel
import omega.isaacbenito.saberapp.authentication.ui.LoginFragment
import omega.isaacbenito.saberapp.authentication.ui.RegCentreFragment
import omega.isaacbenito.saberapp.authentication.ui.RegDataFragment
import omega.isaacbenito.saberapp.di.viewModel.ViewModelKey
import omega.isaacbenito.saberapp.di.worker.AppWorkerFactory
import omega.isaacbenito.saberapp.di.worker.WorkerKey

/**
 * Lligams per a proveir els objectes necessàris en el mòdul d'autenticació
 *
 * @author Isaac Benito
 **/
@Module
abstract class AuthBinds {

    @ContributesAndroidInjector
    abstract fun loginFragment() : LoginFragment

    @ContributesAndroidInjector
    abstract fun regDataFragment() : RegDataFragment

    @ContributesAndroidInjector
    abstract fun regCentreFragment() : RegCentreFragment

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

    @SuppressWarnings("unused")
    @Binds
    @IntoMap
    @WorkerKey(AuthWorker::class)
    abstract fun bindAuthWorker(factory: AuthWorker.Factory): AppWorkerFactory
}