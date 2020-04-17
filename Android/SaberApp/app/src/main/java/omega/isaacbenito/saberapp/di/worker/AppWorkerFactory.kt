package omega.isaacbenito.saberapp.di.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters

/**
 *
 * @author Isaac Benito
 **/
interface AppWorkerFactory {
    fun create(appContext: Context, workerParameters: WorkerParameters): ListenableWorker
}