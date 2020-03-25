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

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.*
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import omega.isaacbenito.saberapp.R
import omega.isaacbenito.saberapp.authentication.model.RegCentreViewModel
import omega.isaacbenito.saberapp.authentication.model.RegisterViewModel
import omega.isaacbenito.saberapp.data.entities.Centre
import omega.isaacbenito.saberapp.databinding.FragmentRegCentreBinding
import omega.isaacbenito.saberapp.databinding.RegCentreItemBinding
import omega.isaacbenito.saberapp.ui.MainActivity
import javax.inject.Inject

/**
 * @author Isaac Benito
 *
 * Vista de selecció del centre educatiu.
 *
 * Implementa el gestor d'interacció de l'adaptador de la llista de la vista.
 *
 */
class RegCentreFragment : Fragment(), CentreAdapter.Interaction {

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

        /**
         * Crea un lligam amb el model de la vista i n'observa els canvis succeïts en la variable
         * d'estat de l'autenticació.
         *
         * En cas que l'autenticació sigui exitosa llança l'activitat principal de l'aplicació.
         */
        registerViewModel.registrationStatus.observe(this, Observer {
            when (it) {
                is AuthSuccess -> startActivity(Intent(context, MainActivity::class.java))
                is AuthError ->
                    when (it.error) {
                        AuthError.NO_INTERNET_ACCESS ->
                            Toast.makeText(context, R.string.no_network, Toast.LENGTH_SHORT).show()
                        AuthError.SERVER_UNREACHABLE_ERROR ->
                            Toast.makeText(context, R.string.server_unreachable, Toast.LENGTH_SHORT)
                                .show()
                    }
            }
        })

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
     * Es crida quan s'associa el fragment a l'activitat que el conté.
     *
     * @param context
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        /**
         * Usa l'instància del component d'autenticació de l'AuthActivity per a ingressar els
         * objectes de l'esquma de l'aplicació en els camps marcats amb l'anotació
         * @Inject
         */
        (activity as AuthActivity).authComponent.inject(this)
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

/**
 * @author Isaac Benito
 *
 * Proporcionen una enquadernació del conjunt de dades de centres educatius
 * per a visualitzar-les dins el RecyclerView.
 *
 * @property interaction listener que gestiona la interacció en polsar un centre.
 */
class CentreAdapter(
    private val interaction: Interaction
) : ListAdapter<Centre, CentreAdapter.CentreVH>(CentreDiffCallback()) {

    /**
     * @author Isaac Benito
     *
     * Estableix el lligam entre les dades i la vista en la que es mostren cada un
     * dels elements de la llista
     *
     * @property binding lligam al recurs de vista dels ítems de la llista
     * @property interaction listener que gestiona la interacció en seleccionar
     *      un item de la llista.
     */
    class CentreVH(
        private val binding: RegCentreItemBinding,
        private val interaction: Interaction
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(centre: Centre) {
            binding.centreTextView.text = centre.name

            binding.setClickListener {
                interaction.onClickCentre(adapterPosition, centre)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CentreVH {
        return CentreVH(
            RegCentreItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), interaction
        )
    }

    override fun onBindViewHolder(holder: CentreVH, position: Int) {
        val centre = getItem(position)
        holder.bind(centre)
    }


    /**
     * @author Isaac Benito
     *
     * Interficie que defineix les interaccions possibles amb els items de la llista.
     *
     */
    interface Interaction {
        fun onClickCentre(position: Int, centre: Centre)
    }
}


private class CentreDiffCallback : DiffUtil.ItemCallback<Centre>() {

    override fun areItemsTheSame(oldItem: Centre, newItem: Centre): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Centre, newItem: Centre): Boolean {
        return oldItem == newItem
    }
}



