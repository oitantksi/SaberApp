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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import omega.isaacbenito.saberapp.api.SaberAppServer
import omega.isaacbenito.saberapp.api.entities.Centre
import javax.inject.Inject

/**
 * @author Isaac Benito
 *
 * Model de la vista de selecció de centre educatiu
 *
 */
class RegCentreViewModel @Inject constructor() {

    @Inject
    lateinit var server: SaberAppServer

    private val _centres = MutableLiveData<List<Centre>>()
    val centres: LiveData<List<Centre>>
        get() = _centres

    init {
        fetchCentres()
    }

    private fun fetchCentres() {
        CoroutineScope(Dispatchers.Main).launch {
            _centres.value = withContext(Dispatchers.IO) {
                return@withContext server.service.getCentres()
            }
        }
    }

    //TODO request centre list
    // Centres hardcoded
//    val centres = listOf("IOC", "IES JOAN MARAGALL", "IES JAUME BALMES")





}