/*
 * This file is part of SaberApp.
 *
 *     SaberApp is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     SaberApp is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with SaberApp.  If not, see <https://www.gnu.org/licenses/>.
 */

package omega.isaacbenito.saberapp.user.model

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import omega.isaacbenito.saberapp.R
import omega.isaacbenito.saberapp.authentication.AccountGlobals
import omega.isaacbenito.saberapp.authentication.AuthResult
import omega.isaacbenito.saberapp.authentication.AuthenticationManager
import omega.isaacbenito.saberapp.data.Result
import omega.isaacbenito.saberapp.data.entities.Centre
import omega.isaacbenito.saberapp.data.entities.User
import omega.isaacbenito.saberapp.data.prefs.PrefStorage
import omega.isaacbenito.saberapp.data.repos.CentreRepository
import omega.isaacbenito.saberapp.data.repos.JocPreguntesRepository
import omega.isaacbenito.saberapp.data.repos.UserRepository
import timber.log.Timber
import java.net.ConnectException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val centreRepository: CentreRepository,
    private val jocPreguntesRepository: JocPreguntesRepository,
    private val authManager: AuthenticationManager,
    private val prefs: PrefStorage
) : ViewModel() {

    companion object {
        const val EDIT_FINISHED = 0
        const val EDIT_NAME = 1
        const val EDIT_SURNAME = 2
        const val EDIT_NICKNAME = 3
        const val EDIT_MAIL = 4
        const val EDIT_PASSWORD = 5
        const val EDIT_CENTRE = 6
        const val ACTION_DONE = 0
        const val ACTION_CANCEL = 1
    }

    private val _user = MediatorLiveData<User>()
    val user: LiveData<User> = _user

    private val _userScore = MediatorLiveData<Int>()
    val userScore: LiveData<Int> = _userScore

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _snackbarText = MutableLiveData<Int>()
    val snackbarMessage: LiveData<Int> = _snackbarText

    private val _editEvent = MutableLiveData<Int>()
    val editEvent: LiveData<Int> = _editEvent

    private val _logoutEvent = MediatorLiveData<Boolean>()
    val logoutEvent: LiveData<Boolean> = _logoutEvent

    private val _centreList = MediatorLiveData<List<Centre>>()
    val centreList: LiveData<List<Centre>> = _centreList

    var firstEditableText = ""
    var secondEditableText = ""
    var thirdEditableText = ""

    private var userMail = prefs.getCurrentUserName()

    init {
        loadUser(true)
        viewModelScope.launch {
            prefs.setCurrentUserId(
                when (val result = userRepository.getUserId(userMail)) {
                    is Result.Success -> result.data
                    else -> 0
                }
            )
        }
    }

    private fun loadUser(forceUpdate: Boolean) {
        _dataLoading.value = true
        viewModelScope.launch {
            when (val userLoadResult = userRepository.getUser(userMail, forceUpdate)) {
                is Result.Success -> {
                    _user.addSource(userLoadResult.data) { _user.value = it }
                    _dataLoading.value = false
                }
                is Result.Error -> {
                    when (userLoadResult.exception) {
                        is ConnectException -> showSnackbarMessage(R.string.server_unreachable)
                        else -> throw UnknownError(userLoadResult.exception.message)
                    }
                }
            }

            when (val scoreLoadResult = jocPreguntesRepository.getUserScore(userMail)) {
                is Result.Success -> _userScore.addSource(scoreLoadResult.data) {
                    _userScore.value = it
                }
                is Result.Error -> {
                    when (scoreLoadResult.exception) {
                        is ConnectException -> showSnackbarMessage(R.string.server_unreachable)
                        else -> throw UnknownError(scoreLoadResult.exception.message)
                    }
                }
            }
        }
    }

    private fun showSnackbarMessage(stringResource: Int) {
        _snackbarText.value = stringResource
    }

    //    fun unregisterUser(): LiveData<AuthState> {
////        return authManager.unregisterUser()
//    }
//

    fun onEditName() {
        _editEvent.value = EDIT_NAME
        firstEditableText = _user.value?.name!!
    }

    fun onEditSurname() {
        _editEvent.value = EDIT_SURNAME
        firstEditableText = _user.value?.surname!!
    }

    fun onEditNickname() {
        _editEvent.value = EDIT_NICKNAME
        firstEditableText = _user.value?.nickname!!
    }

    fun onEditEmail() {
        _editEvent.value = EDIT_MAIL
        firstEditableText = _user.value?.email!!
    }

    fun onEditPassword() {
        _editEvent.value = EDIT_PASSWORD
    }

    fun onEditCentre() {
        _dataLoading.value = true
        viewModelScope.launch {
            when (val loadResult = centreRepository.getCentres(true)) {
                is Result.Success -> {
                    _centreList.addSource(loadResult.data) {_centreList.value = it}
                    _dataLoading.value = false
                    _editEvent.value = EDIT_CENTRE
                }
                is Result.Error -> {
                    when (loadResult.exception) {
                        is ConnectException -> showSnackbarMessage(R.string.server_unreachable)
                        else -> throw UnknownError(loadResult.exception.message)
                    }
                }
            }
        }



    }

    fun onEditAction(action: Int) {
        onEditAction(action, null)
    }

    fun onEditAction(action: Int, centre: Centre? = null) {
        when (action) {
            ACTION_DONE -> {
                val userEdit = _user.value!!
                val userEmail = userEdit.email
                var userEdited = false
                when (_editEvent.value) {
                    EDIT_NAME -> {
                        if (userEdit.name != firstEditableText) {
                            if (firstEditableText != "" && AccountGlobals.isValidNameOrSurname(firstEditableText)) {
                                userEdited = true
                                userEdit.name = firstEditableText
                            } else {
                                _snackbarText.value = R.string.invalid_name
                            }
                        }
                    }
                    EDIT_SURNAME -> {
                        if (userEdit.surname != firstEditableText) {
                            if (firstEditableText != "" && AccountGlobals.isValidNameOrSurname(firstEditableText)) {
                                userEdited = true
                                userEdit.surname = firstEditableText
                            } else {
                                _snackbarText.value = R.string.invalid_name
                            }
                        }
                    }
                    EDIT_NICKNAME -> {
                        if (userEdit.nickname != firstEditableText) {
                            if (firstEditableText != "" && AccountGlobals.isValidNickname(firstEditableText)) {
                                userEdited = true
                                userEdit.nickname = firstEditableText
                            } else {
                                _snackbarText.value = R.string.invalid_nickname
                            }
                        }
                    }
                    EDIT_MAIL -> {
                        if (userEdit.email != firstEditableText) {
                            if (firstEditableText != "" && AccountGlobals.isValidEmail(
                                    firstEditableText
                                )
                            ) {
                                userEdited = true
                                userEdit.name = firstEditableText
                                authManager.updateUserAccountIdentifier(userEdit.email)
                            } else {
                                _snackbarText.value = R.string.invalid_email
                            }
                        }
                    }
                    EDIT_PASSWORD -> {
                        val oldPassword = firstEditableText
                        val newPassword = secondEditableText
                        val passwordRepeat = thirdEditableText
                        if (oldPassword != newPassword) {
                            if (AccountGlobals.isValidPassword(newPassword)) {
                                if (newPassword == passwordRepeat) {
                                    if (authManager.updateUserPassword(oldPassword, newPassword).value == AuthResult.Success) {
                                        viewModelScope.launch {
                                            userRepository.updateUserPassword(
                                                userEmail,
                                                oldPassword,
                                                newPassword
                                            )
                                        }
                                    } else {
                                        _snackbarText.value = R.string.wrong_credentials
                                    }
                                } else {
                                    _snackbarText.value = R.string.invalid_password_repeat
                                }
                            } else {
                                _snackbarText.value = R.string.invalid_password
                            }
                        }

                    }
                    EDIT_CENTRE -> {
                        userEdit.centre = centre?.name ?: throw NullPointerException()
                        userEdited = true
                    }
                }

                if (userEdited && _editEvent.value != EDIT_PASSWORD) {
                    viewModelScope.launch {
                        userRepository.updateUser(userEmail, userEdit)
                        if (_editEvent.value == EDIT_MAIL) {
                            userMail = userEdit.email
                            prefs.setCurrentUserName(userEmail)
                        }
                        loadUser(false)
                    }
                }

                _editEvent.value = EDIT_FINISHED


                Timber.d(userEdit.toString())
            }
            ACTION_CANCEL -> _editEvent.value = EDIT_FINISHED
        }
    }

    fun onLogout() {
        authManager.logout()
        _logoutEvent.value = true
    }


}