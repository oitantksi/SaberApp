package omega.isaacbenito.saberapp.di.viewModel

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

/**
 *
 * @author Isaac Benito
 **/
@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(
        factory: DaggerViewModelFactory
    ): ViewModelProvider.Factory
}