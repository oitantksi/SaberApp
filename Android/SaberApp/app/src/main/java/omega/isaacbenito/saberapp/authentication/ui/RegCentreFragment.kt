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
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import omega.isaacbenito.saberapp.R
import omega.isaacbenito.saberapp.authentication.model.RegCentreViewModel
import omega.isaacbenito.saberapp.authentication.model.RegisterViewModel
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
    lateinit var viewModel: RegCentreViewModel

    @Inject
    lateinit var registerViewModel: RegisterViewModel

    private lateinit var binding: FragmentRegCentreBinding

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
            if (it is AuthSuccess) {
                startActivity(Intent(context, MainActivity::class.java))
            }
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


        binding.centreList.let {
            it.setHasFixedSize(true)
            it.layoutManager = LinearLayoutManager(context)
            it.adapter = CentreAdapter(viewModel.centres, this)
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
    override fun onClickCentre(position: Int, centre: String) {
        registerViewModel.updateCentreData(centre)
    }
}

/**
 * @author Isaac Benito
 *
 * Proporcionen una enquadernació del conjunt de dades de centres educatius
 * per a visualitzar-les dins el RecyclerView.
 *
 * @property centreList Llista de Centres educatius
 * @property interaction listener que gestiona la interacció en polsar un centre.
 */
class CentreAdapter(
    private val centreList: List<String>,
    private val interaction: Interaction
) : RecyclerView.Adapter<CentreAdapter.CentreVH>() {

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

        fun bind(centre: String) {
            binding.centreTextView.text = centre

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

    override fun getItemCount(): Int = centreList.size

    override fun onBindViewHolder(holder: CentreVH, position: Int) {
        holder.bind(centreList[position])
    }


    /**
     * @author Isaac Benito
     *
     * Interficie que defineix les interaccions possibles amb els items de la llista.
     *
     */
    interface Interaction {
        fun onClickCentre(position: Int, centre: String)
    }
}



