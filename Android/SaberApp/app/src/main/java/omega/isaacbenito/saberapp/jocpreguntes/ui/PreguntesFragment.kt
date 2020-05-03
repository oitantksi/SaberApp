package omega.isaacbenito.saberapp.jocpreguntes.ui

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.DaggerFragment
import omega.isaacbenito.saberapp.R
import omega.isaacbenito.saberapp.data.entities.Materia
import omega.isaacbenito.saberapp.data.entities.PreguntaAmbResposta
import omega.isaacbenito.saberapp.databinding.DialogMateriaChecklistBinding
import omega.isaacbenito.saberapp.databinding.FragmentListBinding
import omega.isaacbenito.saberapp.jocpreguntes.model.JocPreguntesVM
import omega.isaacbenito.saberapp.jocpreguntes.ui.adapters.MateriaFilterAdapter
import omega.isaacbenito.saberapp.jocpreguntes.ui.adapters.PreguntaAdapter
import javax.inject.Inject

/**
 * [Fragment] per a mostrar un llistat de preguntes
 *
 * @author Isaac Benito
 **/
class PreguntesFragment : DaggerFragment() {

    private lateinit var binding: FragmentListBinding

    private lateinit var preguntaAdapter: PreguntaAdapter
    private lateinit var materiaFilterAdapter: MateriaFilterAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val jocVM by viewModels<JocPreguntesVM> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

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

        jocVM.materiaFilterList.observe(viewLifecycleOwner, Observer {
            if (::materiaFilterAdapter.isInitialized) {
                materiaFilterAdapter.submitList(it)
            }
        })

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false)

        preguntaAdapter =
            PreguntaAdapter(
                object :
                    PreguntaAdapter.Interaction {
                    override fun onClickPregunta(
                        position: Int,
                        preguntaAmbResposta: PreguntaAmbResposta
                    ) {
                        jocVM.setShowPregunta(preguntaAmbResposta.pregunta.id)
                        findNavController().navigate(
                            R.id.action_preguntesFragment_to_preguntaFragment
                        )
                    }
                }
            )

        binding.recyclerList.let {
            it.setHasFixedSize(true)
            it.layoutManager = LinearLayoutManager(context)
            it.adapter = preguntaAdapter
        }

        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.joc_preguntes_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.filter_action -> {
                showMateriaFilter()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showMateriaFilter() {
        val materiaFilterViewBingding = DataBindingUtil.inflate<DialogMateriaChecklistBinding>(
            layoutInflater, R.layout.dialog_materia_checklist, null, false
        )

        materiaFilterAdapter =
            MateriaFilterAdapter(
                object :
                    MateriaFilterAdapter.Interaction {
                    override fun onClick(materia: Materia) {
                        jocVM.onMateriaFilterSelected(materia)
                    }
                })

        materiaFilterAdapter.submitList(jocVM.materiaFilterList.value)

        materiaFilterViewBingding.dialogListListview.let {
            it.setHasFixedSize(true)
            it.layoutManager = LinearLayoutManager(context)
            it.adapter = materiaFilterAdapter
        }

        val materiaFilterDialog = Dialog(requireContext(), R.style.FooterDialog).apply {
            setContentView(materiaFilterViewBingding.root)
            window?.setGravity(Gravity.BOTTOM)
            window?.setBackgroundDrawable(
                ColorDrawable(
                    ContextCompat.getColor(
                        context,
                        R.color.transparent
                    )
                )
            )
            window?.setWindowAnimations(R.style.FooterDialog)
            window?.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            setCanceledOnTouchOutside(true)
        }

        materiaFilterDialog.setOnCancelListener {
            jocVM.onMateriaFilterApplied(false)
        }

        materiaFilterViewBingding.dialogListApply.setOnClickListener {
            jocVM.onMateriaFilterApplied(true)
            materiaFilterDialog.dismiss()
        }

        materiaFilterDialog.show()
    }
}