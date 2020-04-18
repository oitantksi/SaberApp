package omega.isaacbenito.saberapp.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import omega.isaacbenito.saberapp.ui.MainActivity
import omega.isaacbenito.saberapp.ui.StartActivity


/**
 *
 * @author Isaac Benito
 **/
@Module
abstract class AppModule {

    @ContributesAndroidInjector
    abstract fun contributeInjectorMainActivity() : MainActivity

    @ContributesAndroidInjector
    abstract fun contributeInjectorStartActivity() : StartActivity
}