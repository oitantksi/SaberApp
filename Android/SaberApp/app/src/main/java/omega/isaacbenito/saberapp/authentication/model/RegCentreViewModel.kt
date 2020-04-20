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

package omega.isaacbenito.saberapp.authentication.model

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import omega.isaacbenito.saberapp.R
import omega.isaacbenito.saberapp.data.Result.Error
import omega.isaacbenito.saberapp.data.Result.Success
import omega.isaacbenito.saberapp.data.entities.Centre
import omega.isaacbenito.saberapp.data.remote.server.SaberAppServer
import omega.isaacbenito.saberapp.data.repos.CentreRepository
import java.net.ConnectException
import javax.inject.Inject

/**
 * Model de la vista de selecció de centre educatiu
 *
 * @author Isaac Benito
 * @property centreRepo repositori de dades de centres educatius
 */
class RegCentreViewModel @Inject constructor(
    private val centreRepo: CentreRepository
) : ViewModel() {

    @Inject
    lateinit var server: SaberAppServer

    private val _centres = MediatorLiveData<List<Centre>>()
    val centres: LiveData<List<Centre>> = _centres

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _snackbarText = MutableLiveData<Int>()
    val snackbarMessage: LiveData<Int> = _snackbarText

    init {
        fetchCentres()
    }

    /**
     * Recupera els centres del repositori de centres.
     */
    private fun fetchCentres() {
        _dataLoading.value = true
        viewModelScope.launch {
            when (val fetchResult = centreRepo.getCentres(true)) {
                is Success -> {
                    _centres.addSource(fetchResult.data) {_centres.value = it }
                    _dataLoading.value = false
                }
                is Error -> when (fetchResult.exception) {
                    is ConnectException -> showSnackbarMessage(R.string.server_unreachable)
                    else -> throw UnknownError(fetchResult.exception.message)
                }
            }
        }
    }

    private fun showSnackbarMessage(stringResource: Int) {
        _snackbarText.value = stringResource
    }
}