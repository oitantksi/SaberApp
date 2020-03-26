package omega.isaacbenito.saberapp

import androidx.lifecycle.Observer


/**
 * Envoltori per a dades que representin un event exposades a través d'un objecte LiveData
 *
 * @author Isaac Benito
 **/
open class Event<out T>(private val content: T) {

    @Suppress("MemberVisibilityCanBePrivate")
    var hasBeenHandled = false
        private set

    /**
     * Retorna el contingut de l'event i evita que es torni a usar.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Retorna el contingut, encara que ja hagi sigut atès.
     */
    fun peekContent(): T = content
}

/**
 * Un [Observer] per [Event]s que simplifica la rutina de revisar si l'[Event] ja ha sigut atès.
 *
 * Només es crida [onEventUnhandledContent] si no s'han atès els continguts de l'[Event].
 */
class EventObserver<T>(private val onEventUnhandledContent: (T) -> Unit) : Observer<Event<T>> {
    override fun onChanged(event: Event<T>?) {
        event?.getContentIfNotHandled()?.let {
            onEventUnhandledContent(it)
        }
    }
}