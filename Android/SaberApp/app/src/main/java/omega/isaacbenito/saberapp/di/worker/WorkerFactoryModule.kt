package omega.isaacbenito.saberapp.di.worker

import androidx.work.WorkerFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import omega.isaacbenito.saberapp.authentication.impl.AuthWorker

/**
 *
 * @author Isaac Benito
 **/
@Module
abstract class WorkerFactoryModule {

    @Binds
    abstract fun bindWorkerFactory(
        factory: DaggerWorkerFactory
    ): WorkerFactory

    @SuppressWarnings("unused")
    @Binds
    @IntoMap
    @WorkerKey(AuthWorker::class)
    abstract fun bindAuthWorker(factory: AuthWorker.Factory): AppWorkerFactory
}