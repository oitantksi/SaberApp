package omega.isaacbenito.saberapp.jocpreguntes.model

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import omega.isaacbenito.saberapp.data.Result
import omega.isaacbenito.saberapp.data.entities.*
import omega.isaacbenito.saberapp.data.prefs.PrefStorage
import omega.isaacbenito.saberapp.data.repos.JocPreguntesRepository
import omega.isaacbenito.saberapp.data.repos.UserRepository
import omega.isaacbenito.saberapp.utils.combine
import timber.log.Timber
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Model de les vistes del Joc de Preguntes
 *
 * @author Isaac Benito
 **/
@Singleton
class JocPreguntesVM @Inject constructor(
    private val jocRepo: JocPreguntesRepository,
    private val userRepo: UserRepository,
    private val prefs: PrefStorage
) : ViewModel() {

    companion object {
        const val MATERIA_GLOBAL = 0L
        const val MATERIA_GEOGRAFIA = 1L
        const val MATERIA_HISTORIA = 2L
        const val MATERIA_ARTS = 3L
        const val MATERIA_MATES = 4L

        const val ACTION_CURRENT = 0
        const val ACTION_OLD = 1
        const val ACTION_REVIEW = 2
        const val ACTION_SCORE = 3

        val CLASSIFICATION_SCOPES = listOf("GLOBAL", "LOCAL")
        private const val _SCOPE_GLOBAL = 0
        private const val _SCOPE_LOCAL = 1
    }

    private val _currentUser = MutableLiveData<User>()
    val currentUserId = prefs.getCurrentUserId()

    private val _action = MutableLiveData<Int>()

    private val cal = Calendar.getInstance()

    private val _resposta = MutableLiveData<Int?>()
    val respostaEvent : LiveData<Int?> = _resposta

    private val _confirmarResposta = MutableLiveData<Boolean>(false)
    val confirmarResposta : LiveData<Boolean> = _confirmarResposta

    private val _showRevisioResposta = MutableLiveData<Boolean>(false)
    val showRevisioResposta : LiveData<Boolean> = _showRevisioResposta

    private val _listMateries = MediatorLiveData<List<Materia>>()
    private val _materiaFilterList = MutableLiveData<Map<Long, Boolean>>()
    private var _materiaFilterChanges: Map<Long, Boolean>? = null

    val materiaFilterList: LiveData<List<Pair<Materia, Boolean>>> =
        Transformations.map(_listMateries.combine(_materiaFilterList)) {
            if (_materiaFilterList.value.isNullOrEmpty()) {
                initMateriaFilterList()
                emptyList()
            } else {
                sequence {
                    _listMateries.value?.forEach {
                        yield(
                            Pair(
                                it,
                                _materiaFilterList.value?.get(it.id) ?: true
                            )
                        )
                    }
                }.toList()
            }
        }

    private val _listPreguntes = MediatorLiveData<List<PreguntaAmbResposta>>()

    val listPreguntes = Transformations.map(_listPreguntes.combine(_action, _materiaFilterList)) {
        if (_listPreguntes.value == null || _listPreguntes.value?.isEmpty()!!) {
            emptyList()
        } else {
            _listPreguntes.value
                ?.filter { !it.isTodayPregunta() }
                ?.filter {
                    _materiaFilterList.value?.get(it.pregunta.materia) ?: true
                }
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

    private val _scores = MediatorLiveData<List<ScoreWithUserAndMateria>>()
    private val _materiaClassification = MutableLiveData(MATERIA_GLOBAL)
    private val _scopeClassification = MutableLiveData(_SCOPE_GLOBAL)

    private val _userPosition = MutableLiveData<Int>()
    val userPosition: LiveData<Int> = _userPosition

    private val _globalClassification = MutableLiveData<List<Pair<UserWithPicture, Int>>>()
    val classification: LiveData<List<Triple<UserWithPicture, Int, Int>>> =
        Transformations.map(_scores.combine(_materiaClassification, _scopeClassification)) {
            Timber.d("New scores list: $it")
            if (_scores.value != null) {
                if (_globalClassification.value == null || _globalClassification.value?.isEmpty()!!) {
                    _globalClassification.value = _scores.value?.groupBy { it.user }
                        ?.mapValues { it.value.sumBy { it.score.score } }
                        ?.toList()
                        ?.sortedBy { it.second }
                        ?.asReversed()
                }

                val userCentre = _currentUser.value?.centre ?: throw NullPointerException()

                var classification: List<Pair<UserWithPicture, Int>>? = null
                if (_materiaClassification.value == MATERIA_GLOBAL) {
                    if (_scopeClassification.value == _SCOPE_LOCAL) {
                        classification =
                            _globalClassification.value?.filter { it.first.user.centre == userCentre }
                    } else {
                        classification = _globalClassification.value
                    }
                } else {
                    classification = _scores.value
                        ?.filter {
                            when (_scopeClassification.value) {
                                _SCOPE_GLOBAL -> it.materia.id == _materiaClassification.value
                                _SCOPE_LOCAL -> {
                                    it.materia.id == _materiaClassification.value
                                            && it.user.user.centre == userCentre
                                }
                                else -> false
                            }
                        }
                        ?.sortedBy { it.score.score }
                        ?.asReversed()
                        ?.map { it.user to it.score.score }
                }
                _userPosition.value =
                    classification?.indexOfFirst { it.first.user == _currentUser.value }
                classification?.map { Triple(it.first, it.second, classification.indexOf(it)) }
            } else {
                emptyList()
            }
        }

    val userScoreByMateria: LiveData<List<Triple<Materia, Int, Int>>> =
        Transformations.map(_scores.combine(_listMateries, _globalClassification)) {
            if (_scores.value != null && _listMateries.value != null) {
                val userId = _currentUser.value?.id ?: throw NullPointerException()
                val userScore =
                    _globalClassification.value?.firstOrNull { it.first.user.id == userId }
                val scores = mutableListOf<Triple<Materia, Int, Int>>(
                    Triple(
                        Materia(MATERIA_GLOBAL, "Global"),
                        userScore?.second ?: 0,
                        _globalClassification.value?.indexOf(userScore) ?: -1
                    )
                )
                _listMateries.value?.sortedBy { it.name }
                    ?.forEach { materia ->
                        val scoreWithUserAndMateria = _scores.value
                            ?.firstOrNull { it.materia == materia && it.user.user.id == userId }
                        val score = scoreWithUserAndMateria?.score?.score ?: 0
                        val position = _scores.value?.filter { it.materia == materia }
                            ?.sortedBy { it.score.score }?.indexOf(scoreWithUserAndMateria) ?: -1
                        scores.add(Triple(materia, score, position))
                    }

                scores
            } else {
                emptyList<Triple<Materia, Int, Int>>()
            }
        }

    init {
        load()
    }

    fun setup(action: Int) {
        _action.value = action
        when (action) {
            ACTION_CURRENT -> _showRevisioResposta.value = false
        }
    }

    private fun load() {
        viewModelScope.launch {
            loadUser()
            loadMateries()
            loadPreguntes()
            loadScores()
        }
    }

    private suspend fun loadUser() {
        when (val result = userRepo.getUser(prefs.getCurrentUserId())) {
            is Result.Success -> _currentUser.value = result.data
            is Result.Error -> Timber.d(result.exception)
        }
    }

    private suspend fun loadPreguntes() {
        when (val loadResult = jocRepo.getPreguntesAmbRespostaByUser(prefs.getCurrentUserName())) {
            is Result.Success -> {
                _listPreguntes.addSource(loadResult.data) { _listPreguntes.value = it }
            }
            is Result.Error -> Timber.d(loadResult.exception)
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

    private suspend fun loadScores() {
        when (val loadResult = jocRepo.getScores()) {
            is Result.Success -> {
                _scores.addSource(loadResult.data) { _scores.value = it }
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
        viewModelScope.launch {
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

    fun onMateriaFilterSelected(materia: Materia) {
        if (_materiaFilterChanges == null) {
            _materiaFilterChanges = _materiaFilterList.value!!.map {
                if (it.key == materia.id) {
                    it.key to (!it.value)
                } else {
                    it.key to it.value
                }
            }.toMap()
        } else {
            _materiaFilterChanges = _materiaFilterChanges!!.map {
                if (it.key == materia.id) {
                    it.key to (!it.value)
                } else {
                    it.key to it.value
                }
            }.toMap()
        }
    }

    fun onMateriaFilterApplied(apply: Boolean) {
        if (apply) {
            if (_materiaFilterChanges != _materiaFilterList.value) {
                _materiaFilterList.value = _materiaFilterChanges
            }
        } else {
            _materiaFilterChanges = null
        }
    }

    private fun initMateriaFilterList() {
        if (_materiaFilterList.value.isNullOrEmpty()) {
            _materiaFilterList.value = _listMateries.value?.map { it.id to true }?.toMap()
        }
    }

    fun onClassificationScopeChanged(scope: Int) {
        _scopeClassification.value = scope
    }

    fun onClassificationMateriaChanged(materiaId: Long) {
        _materiaClassification.value = materiaId
    }

}
