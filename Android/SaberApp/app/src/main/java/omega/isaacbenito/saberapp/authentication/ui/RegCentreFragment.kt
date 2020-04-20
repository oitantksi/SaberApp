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

package omega.isaacbenito.saberapp.authentication.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment
import omega.isaacbenito.saberapp.R
import omega.isaacbenito.saberapp.authentication.AuthResult
import omega.isaacbenito.saberapp.authentication.model.RegCentreViewModel
import omega.isaacbenito.saberapp.authentication.model.RegisterViewModel
import omega.isaacbenito.saberapp.data.entities.Centre
import omega.isaacbenito.saberapp.databinding.FragmentRegCentreBinding
import omega.isaacbenito.saberapp.ui.MainActivity
import omega.isaacbenito.saberapp.user.ui.CentreAdapter
import javax.inject.Inject

/**
 * @author Isaac Benito
 *
 * Vista de selecció del centre educatiu.
 *
 * Implementa el gestor d'interacció de l'adaptador de la llista de la vista.
 *
 */
class RegCentreFragment : DaggerFragment(), CentreAdapter.Interaction {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val regCentreViewModel by viewModels<RegCentreViewModel> { viewModelFactory }

    private val registerViewModel by viewModels<RegisterViewModel> { viewModelFactory }

    private lateinit var binding: FragmentRegCentreBinding

    private lateinit var centreAdapter: CentreAdapter

    /**
     * Es crida en crear el fragment.
     *
     * @param savedInstanceState
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        regCentreViewModel.centres.observe(this, Observer {
            centreAdapter.submitList(it)
        })
    }

    /**
     * Es crida en crear la vista del fragment.
     * Estableix la vista de la pantalla i lliga el fragment amb les vistes necessàries
     * per a mostrar una llista de centres educatius.
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_reg_centre, container, false)

        /**
         * Crea un lligam amb el model de la vista i n'observa els canvis succeïts en la variable
         * d'estat de l'autenticació.
         *
         * En cas que l'autenticació sigui exitosa llança l'activitat principal de l'aplicació.
         */
        registerViewModel.registrationStatus.observe(viewLifecycleOwner, Observer {
            when (it) {
                is AuthResult.Success -> startActivity(Intent(context, MainActivity::class.java))
                is AuthResult.Error ->
                    when (it.error) {
                        AuthResult.Error.NO_INTERNET_ACCESS ->
                            Snackbar.make(binding.root, R.string.no_network, Snackbar.LENGTH_SHORT).show()
                        AuthResult.Error.SERVER_UNREACHABLE_ERROR ->
                            Snackbar.make(binding.root, R.string.server_unreachable, Snackbar.LENGTH_SHORT)
                                .show()
                    }
            }
        })

        regCentreViewModel.dataLoading.observe(viewLifecycleOwner, Observer {
            if(it) {
                binding.loadingSpinnerLayout.visibility = View.VISIBLE
            } else {
                binding.loadingSpinnerLayout.visibility = View.GONE
            }
        })

        regCentreViewModel.snackbarMessage.observe(viewLifecycleOwner, Observer {
            Snackbar.make(binding.root, resources.getString(it), Snackbar.LENGTH_SHORT).show()
        })

        centreAdapter = CentreAdapter(this)


        binding.centreList.let {
            it.setHasFixedSize(true)
            it.layoutManager = LinearLayoutManager(context)
            it.adapter = centreAdapter
            it.addItemDecoration(DividerItemDecoration(context, VERTICAL))
        }

        return binding.root
    }

    /**
     * Es crida quan l'usuari selecciona un centre educatiu.
     *
     * Envia les dades del centre al model de la vista de registre per a procedir al
     * registre de l'usuari.
     */
    override fun onClickCentre(position: Int, centre: Centre) {
        binding.loadingSpinnerLayout.visibility = View.VISIBLE
        registerViewModel.updateCentreData(centre.name)
    }
}

