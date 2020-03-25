package omega.isaacbenito.saberapp.data

import omega.isaacbenito.saberapp.data.Result.Success

/**
 * Classe genèrica que encapsula un valor amb el resultat de la seva recuperació
 *
 * @author Isaac Benito
 *
 * @param <T>
 */
sealed class Result<out R> {

    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
    object Loading : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
            Loading -> "Loading"
        }
    }
}

/**
 * `true` si [Result] es del tipus [Success] i conté un objecte no null [Success.data].
 */
val Result<*>.succeeded
    get() = this is Success && data != null