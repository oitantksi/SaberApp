package omega.isaacbenito.saberapp.jocpreguntes.model

import android.util.Log.d
import androidx.lifecycle.*
import kotlinx.coroutines.*
import omega.isaacbenito.saberapp.data.Result
import omega.isaacbenito.saberapp.data.entities.Materia
import omega.isaacbenito.saberapp.data.entities.PreguntaAmbResposta
import omega.isaacbenito.saberapp.data.entities.Resposta
import omega.isaacbenito.saberapp.data.prefs.PrefStorage
import omega.isaacbenito.saberapp.data.repos.JocPreguntesRepository
import omega.isaacbenito.saberapp.utils.combine
import timber.log.Timber
import java.lang.IllegalArgumentException
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Model de les vistes del Joc de Preguntes
 *
 * @author Isaac Benito
 **/
@Singleton
class JocPreguntesViewModel @Inject constructor(
    private val jocRepo: JocPreguntesRepository,
    private val prefs: PrefStorage
) : ViewModel() {

    companion object {
        const val MATERIA_GEOGRAFIA = 1L
        const val MATERIA_HISTORIA = 2L
        const val MATERIA_ARTS = 3L
        const val MATERIA_MATES = 4L

        const val ACTION_CURRENT = 0
        const val ACTION_OLD = 1
        const val ACTION_REVIEW = 2
    }

    private val viewModelScope = CoroutineScope(Dispatchers.Main)

    private val _action = MutableLiveData<Int>()

    private val cal = Calendar.getInstance()

    private val _resposta = MutableLiveData<Int?>()
    val respostaEvent : LiveData<Int?> = _resposta

    private val _confirmarResposta = MutableLiveData<Boolean>(false)
    val confirmarResposta : LiveData<Boolean> = _confirmarResposta

    private val _showRevisioResposta = MutableLiveData<Boolean>(false)
    val showRevisioResposta : LiveData<Boolean> = _showRevisioResposta

    private val _listMateries = MediatorLiveData<List<Materia>>()
    val listMateries: LiveData<List<Materia>> = _listMateries

    private val _listPreguntes = MediatorLiveData<List<PreguntaAmbResposta>>()

    val listPreguntes = Transformations.map(_listPreguntes.combine(_action)) {
        if (_listPreguntes.value == null || _listPreguntes.value?.isEmpty()!!) {
            emptyList()
        } else {
            _listPreguntes.value
                ?.filter { !it.isTodayPregunta() }
                ?.run {
                    when(_action.value) {
                        ACTION_REVIEW -> filter { it.resposta != null }
                            .sortedBy { it.resposta?.dataResposta }
                        ACTION_OLD -> filter { it.resposta == null }.sortedBy { it.pregunta.date }
                        else -> throw UnsupportedOperationException()
                    }
                }
                ?.reversed()
        }
    }

    private val _currentPreguntaId = MutableLiveData<Long>()

    private val _currentPregunta: LiveData<PreguntaAmbResposta> =
        Transformations.map(_listPreguntes.combine(_action, _currentPreguntaId)) {
            if (_listPreguntes.value == null || _listPreguntes.value?.isEmpty()!!) {
                null
            } else {
                _listPreguntes.value?.firstOrNull {
                    when(_action.value) {
                        ACTION_CURRENT -> it.isTodayPregunta()
                        ACTION_REVIEW, ACTION_OLD -> it.pregunta.id == _currentPreguntaId.value
                        else -> throw UnknownError()
                    }
                } ?: throw NullPointerException()
            }
        }

    val currentPregunta: LiveData<PreguntaAmbResposta> = _currentPregunta

    init {
        load()
    }

    fun setup(action: Int) {
        _action.value = action
        if (action == ACTION_CURRENT) {
            _showRevisioResposta.value = false
        }
    }

    private fun load() {
        viewModelScope.launch {
            loadMateries()
            loadPreguntes()
        }
    }

    private suspend fun loadPreguntes() {
        when (val result = jocRepo.getPreguntesAmbRespostaByUser(prefs.getCurrentUserName())) {
            is Result.Success -> {
                _listPreguntes.addSource(result.data) { _listPreguntes.value = it }
            }
            is Result.Error -> Timber.d(result.exception)
        }
    }

    private suspend fun loadMateries() {
        when (val loadResult = jocRepo.getMateries()) {
            is Result.Success -> {
                _listMateries.addSource(loadResult.data) { _listMateries.value = it }
            }
            is Result.Error -> Timber.d(loadResult.exception)
        }
    }

    fun setShowPregunta(preguntaId: Long) {
        _currentPreguntaId.value = preguntaId
        _resposta.value = null
        when(_action.value) {
            ACTION_OLD -> {
                _showRevisioResposta.value = false
                _resposta.value = null
            }
            ACTION_REVIEW -> _showRevisioResposta.value = true
        }
    }

    fun onPreguntaAnswered(resposta: Int) {
        when(_action.value) {
            ACTION_CURRENT -> {
                _resposta.value = resposta
                saveResposta() }
            ACTION_OLD -> {
                _resposta.value = resposta
                _confirmarResposta.value = true
            }
        }
    }

    fun onRespostaConfirmada(confirmacio: Boolean) {
        _confirmarResposta.value = false
        if (confirmacio) {
            saveResposta()
            _showRevisioResposta.value = true
        } else {
            _resposta.value = null
        }
    }

    private fun saveResposta() {
        Timber.d("viewModelScope.isActive ${viewModelScope.isActive.toString()}")
        viewModelScope.launch {
            Timber.d("saving")
            jocRepo.setResposta(
                Resposta(
                    prefs.getCurrentUserId(),
                    _currentPregunta.value?.pregunta?.id!!,
                    _resposta.value!!,
                    cal.time
                )
            )
        }
    }

    private fun PreguntaAmbResposta.isTodayPregunta(): Boolean {
        val calPregunta = Calendar.getInstance()
        calPregunta.time = this.pregunta.date
        return (calPregunta.get(Calendar.YEAR) == cal.get(Calendar.YEAR) &&
                calPregunta.get(Calendar.DAY_OF_YEAR) == cal.get(Calendar.DAY_OF_YEAR))
    }

}