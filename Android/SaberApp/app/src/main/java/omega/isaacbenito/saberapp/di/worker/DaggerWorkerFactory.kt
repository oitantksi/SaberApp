package omega.isaacbenito.saberapp.di.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import javax.inject.Inject
import javax.inject.Provider

/**
 * Creador d'objectes Worker per a sobrevenir la incapacitat d'injectar parametres en el constructor.
 *
 * @author Isaac Benito
 **/
class DaggerWorkerFactory @Inject constructor(
    private val creators: Map<Class<out ListenableWorker>, @JvmSuppressWildcards Provider<AppWorkerFactory>>
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        val creator: Provider<out AppWorkerFactory> = creators[Class.forName(workerClassName)]
            ?: creators.asIterable().firstOrNull { Class.forName(workerClassName).isAssignableFrom(it.key) }?.value
            ?: throw IllegalArgumentException("Unknown Worker class $workerClassName")

        return creator.get().create(appContext, workerParameters)
    }
}
