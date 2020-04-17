package omega.isaacbenito.saberapp.jocpreguntes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.DaggerFragment
import omega.isaacbenito.saberapp.R
import omega.isaacbenito.saberapp.data.entities.PreguntaAmbResposta
import omega.isaacbenito.saberapp.databinding.FragmentListBinding
import omega.isaacbenito.saberapp.jocpreguntes.model.JocPreguntesViewModel
import javax.inject.Inject

/**
 * [Fragment] per a mostrar un llistat de preguntes
 *
 * @author Isaac Benito
 **/
class PreguntesFragment : DaggerFragment(), PreguntaAdapter.Interaction {

    lateinit var binding: FragmentListBinding

    lateinit var preguntaAdapter: PreguntaAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val jocVM by viewModels<JocPreguntesViewModel> { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        jocVM.listPreguntes.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                preguntaAdapter.submitList(it)
            }
        })

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false)

        preguntaAdapter =
            PreguntaAdapter(this)

        binding.recyclerList.let {
            it.setHasFixedSize(true)
            it.layoutManager = LinearLayoutManager(context)
            it.adapter = preguntaAdapter
        }

        return binding.root
    }


//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.joc_preguntes_menu, menu)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.filter_action ->
//        }
//    }

    override fun onClickPregunta(position: Int, preguntaAmbResposta: PreguntaAmbResposta) {
        jocVM.setShowPregunta(preguntaAmbResposta.pregunta.id)
        findNavController().navigate(
            R.id.action_preguntesFragment_to_preguntaFragment
        )
    }
}