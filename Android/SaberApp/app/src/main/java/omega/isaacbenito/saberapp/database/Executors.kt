package omega.isaacbenito.saberapp.database

import java.util.concurrent.Executors

private val IO_EXECUTOR = Executors.newSingleThreadExecutor()

fun ioThred(f : () -> Unit) {
    IO_EXECUTOR.execute(f)
}