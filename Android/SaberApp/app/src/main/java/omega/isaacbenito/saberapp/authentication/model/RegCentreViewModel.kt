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

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import omega.isaacbenito.saberapp.data.Result.Success
import omega.isaacbenito.saberapp.data.entities.Centre
import omega.isaacbenito.saberapp.data.remote.server.SaberAppServer
import omega.isaacbenito.saberapp.data.repos.CentreRepository
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

    private val _centres = MutableLiveData<List<Centre>>()
    val centres: LiveData<List<Centre>>
        get() = _centres

    init {
        fetchCentres()
    }

    /**
     * Recupera els centres del repositori de centres.
     */
    private fun fetchCentres() {
        viewModelScope.launch {
            val fetchResult = centreRepo.getCentres(true)

            when (fetchResult) {
                is Success -> _centres.value = fetchResult.data.value
                else -> _centres.value = emptyList()
            }

        }
    }
}