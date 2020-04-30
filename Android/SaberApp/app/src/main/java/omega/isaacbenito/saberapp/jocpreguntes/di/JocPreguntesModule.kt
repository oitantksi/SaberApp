package omega.isaacbenito.saberapp.jocpreguntes.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import omega.isaacbenito.saberapp.di.viewModel.ViewModelKey
import omega.isaacbenito.saberapp.jocpreguntes.model.JocPreguntesVM
import omega.isaacbenito.saberapp.jocpreguntes.ui.PreguntaFragment
import omega.isaacbenito.saberapp.jocpreguntes.ui.PreguntesFragment
import omega.isaacbenito.saberapp.jocpreguntes.ui.ScoreFragment

/**
 * Mòdul que afegeix els models i les vistes del component del Joc de Preguntes
 * en el gràfic de l'aplicació
 *
 * @author Isaac Benito
 **/
@Module
abstract class JocPreguntesModule {

    @SuppressWarnings("unused")
    @Binds
    @IntoMap
    @ViewModelKey(JocPreguntesVM::class)
    abstract fun bindJocPreguntesViewModel(viewModel: JocPreguntesVM): ViewModel

    @ContributesAndroidInjector()
    internal abstract fun preguntaFragment() : PreguntaFragment

    @ContributesAndroidInjector()
    internal abstract fun preguntesFragment() : PreguntesFragment

    @ContributesAndroidInjector()
    internal abstract fun scoreFragment(): ScoreFragment
}