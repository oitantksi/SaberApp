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
import omega.isaacbenito.saberapp.authentication.ui.AuthState
import omega.isaacbenito.saberapp.data.Result
import omega.isaacbenito.saberapp.data.entities.Centre
import omega.isaacbenito.saberapp.data.entities.User
import omega.isaacbenito.saberapp.data.repos.UserRepository
import omega.isaacbenito.saberapp.di.LoggedUserScope
import timber.log.Timber
import java.net.ConnectException
import javax.inject.Inject

@LoggedUserScope
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
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

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _snackbarText = MutableLiveData<Int>()
    val snackbarMessage: LiveData<Int> = _snackbarText

    private val _editEvent = MutableLiveData<Int>()
    val editEvent: LiveData<Int> = _editEvent


    fun start(userMail: String) {
        loadUser(userMail, true)
    }

    private fun loadUser(userMail: String, forceUpdate: Boolean) {
        _dataLoading.value = true
        viewModelScope.launch {
            val loadResult = userRepository.getUser(userMail, forceUpdate)

            when (loadResult) {
                is Result.Success -> {
                    _user.addSource(loadResult.data, Observer { _user.value = it })
                    _dataLoading.value = false
                }
                is Result.Error -> {
                    when (loadResult) {
                        is ConnectException -> showSnackbarMessage(R.string.server_unreachable)
                        else -> throw UnknownError(loadResult.exception.message)
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
    fun logoutUser(): LiveData<AuthState> {
        return TODO()
//        return authManager.logoutUser()
    }

    private val _centres = MutableLiveData<List<Centre>>()
    val centres: LiveData<List<Centre>>
        get() = _centres

    fun getCentres() {
//        viewModelScope.launch {
//            _centres.value = withContext(Dispatchers.IO) {
//                return@withContext server.apiService.getCentres()
//            }
//        }
    }

    fun onEditName() {
        _editEvent.value = EDIT_NAME
    }

    fun onEditSurname() {
        _editEvent.value = EDIT_SURNAME
    }

    fun onEditNickname() {
        _editEvent.value = EDIT_NICKNAME
    }

    fun onEditEmail() {
        _editEvent.value = EDIT_MAIL
    }

    fun onEditPassword() {
        _editEvent.value = EDIT_PASSWORD
    }

    fun onEditCentre() {
        _editEvent.value = EDIT_CENTRE
    }

    fun onEditAction(action: Int, firstEdit: String, secondEdit: String) {
        when (action) {
            ACTION_DONE -> {
                val userEdit = _user.value!!
                var userEdited = false
                when (_editEvent.value) {
                    EDIT_NAME -> {
                        if (userEdit.name != firstEdit) {
                            userEdited = true
                            userEdit.name = firstEdit
                        }
                    }
                    EDIT_SURNAME -> {
                        if (userEdit.name != firstEdit) {
                            userEdited = true
                            userEdit.surname = firstEdit
                        }
                    }
                    EDIT_NICKNAME -> {
                        if (userEdit.name != firstEdit) {
                            userEdited = true
                            userEdit.nickname = firstEdit
                        }
                    }
                    EDIT_MAIL -> {
                        if (userEdit.name != firstEdit) {
                            userEdited = true
                            userEdit.email = firstEdit
                        }
                    }
                    EDIT_PASSWORD -> {
                        if (userEdit.name != firstEdit) {
                            userEdited = true
                            TODO()
                        }
                    }
                    EDIT_CENTRE -> {
                        if (userEdit.name != firstEdit) {
                            userEdited = true
                            TODO()
                        }
                    }
                }
                _editEvent.value = EDIT_FINISHED

                viewModelScope.launch { userRepository.updateUser(userEdit) }

                Timber.d(userEdit.toString())
            }
            ACTION_CANCEL -> _editEvent.value = EDIT_FINISHED
        }
    }

    fun onLogout() {
        //TODO
    }


}