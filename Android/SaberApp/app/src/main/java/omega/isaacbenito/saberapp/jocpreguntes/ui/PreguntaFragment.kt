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
import dagger.android.support.DaggerFragment
import omega.isaacbenito.saberapp.R
import omega.isaacbenito.saberapp.data.entities.PreguntaAmbResposta
import omega.isaacbenito.saberapp.databinding.DialogPreguntaConfirmationBindingImpl
import omega.isaacbenito.saberapp.databinding.FragmentPreguntaBinding
import omega.isaacbenito.saberapp.jocpreguntes.model.JocPreguntesViewModel
import timber.log.Timber
import javax.inject.Inject

class PreguntaFragment : DaggerFragment() {

    private lateinit var binding: FragmentPreguntaBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val jocVM by viewModels<JocPreguntesViewModel> { viewModelFactory }

    private lateinit var currentPregunta: PreguntaAmbResposta

    private var revisioResposta = false

    private lateinit var confirmationDialog: Dialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pregunta, container, false)

        binding.viewModel = jocVM

        jocVM.currentPregunta.observe(viewLifecycleOwner, Observer {
                if (it != null) {
                    currentPregunta = it
                    Timber.d(currentPregunta.toString())
                    setPreguntaView()
                }
        })

        jocVM.respostaEvent.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                setRespostaSleccionadaView(it)
            }
        })

        jocVM.confirmarResposta.observe(viewLifecycleOwner, Observer {
            if (it) {
                showConfirmationDialog()
            } else {
                if (::confirmationDialog.isInitialized) {
                    confirmationDialog.dismiss()
                }
            }
        })

        jocVM.showRevisioResposta.observe(viewLifecycleOwner, Observer {
            revisioResposta = it
            if (::currentPregunta.isInitialized) {
                setRevisioRespostaView()
            }
        })

        return binding.root
    }

    private fun setPreguntaView() {
        val pregunta = currentPregunta.pregunta
        val resposta = currentPregunta.resposta

        binding.pregunta = pregunta

        binding.preguntaMateriaIcon.setImageDrawable(ContextCompat.getDrawable(context!!,
            when (pregunta.materia) {
                JocPreguntesViewModel.MATERIA_GEOGRAFIA -> R.drawable.materia_geografia_icon_carved
                JocPreguntesViewModel.MATERIA_HISTORIA -> R.drawable.materia_historia_icon_carved
                JocPreguntesViewModel.MATERIA_ARTS -> R.drawable.materia_arts_icon_carved
                JocPreguntesViewModel.MATERIA_MATES -> R.drawable.materia_mates_icon_carved
                else -> R.drawable.applogo
            }))

        val materiaColor = ContextCompat.getColor(context!!,
            when (pregunta.materia) {
                JocPreguntesViewModel.MATERIA_GEOGRAFIA -> R.color.materia_geografia
                JocPreguntesViewModel.MATERIA_HISTORIA -> R.color.materia_història
                JocPreguntesViewModel.MATERIA_ARTS -> R.color.materia_art
                JocPreguntesViewModel.MATERIA_MATES -> R.color.materia_matematiques
                else -> R.color.colorAccent
            })

        binding.preguntaCardView.setCardBackgroundColor(materiaColor)

        binding.answer1.setCardBackgroundColor(materiaColor)
        binding.answer2.setCardBackgroundColor(materiaColor)
        binding.answer3.setCardBackgroundColor(materiaColor)
        binding.answer4.setCardBackgroundColor(materiaColor)

        if (resposta != null) {

            setRespostaSleccionadaView(resposta.resposta)


            setRevisioRespostaView()
        }
    }

    private fun setRespostaSleccionadaView(r: Int) {
        val transparent = ContextCompat.getColor(context!!, android.R.color.transparent)

        binding.answer1Layout.setBackgroundColor(transparent)
        binding.answer2Layout.setBackgroundColor(transparent)
        binding.answer3Layout.setBackgroundColor(transparent)
        binding.answer4Layout.setBackgroundColor(transparent)

        val respostaSeleccionadaColor = ContextCompat.getColor(context!!,
            R.color.resposta_seleccionada)

        when(r) {
            1 -> binding.answer1Layout.setBackgroundColor(respostaSeleccionadaColor)
            2 -> binding.answer2Layout.setBackgroundColor(respostaSeleccionadaColor)
            3 -> binding.answer3Layout.setBackgroundColor(respostaSeleccionadaColor)
            4 -> binding.answer4Layout.setBackgroundColor(respostaSeleccionadaColor)
        }
    }

    private fun setRevisioRespostaView() {
        if (revisioResposta) {
            when (currentPregunta.pregunta.right_ans) {
                1 -> binding.answer1Right.visibility = View.VISIBLE
                2 -> binding.answer2Right.visibility = View.VISIBLE
                3 -> binding.answer3Right.visibility = View.VISIBLE
                4 -> binding.answer4Right.visibility = View.VISIBLE
            }

            if (currentPregunta.pregunta.right_ans != currentPregunta.resposta?.resposta) {
                when (currentPregunta.resposta?.resposta) {
                    1 -> binding.answer1Wrong.visibility = View.VISIBLE
                    2 -> binding.answer2Wrong.visibility = View.VISIBLE
                    3 -> binding.answer3Wrong.visibility = View.VISIBLE
                    4 -> binding.answer4Wrong.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun showConfirmationDialog() {
        val confirmationDialogBinding = DataBindingUtil.inflate<DialogPreguntaConfirmationBindingImpl>(
            layoutInflater, R.layout.dialog_pregunta_confirmation, null, false
        )

        confirmationDialogBinding.jocVM = jocVM

        confirmationDialog = Dialog(context!!, R.style.FooterDialog).apply {
            setContentView(confirmationDialogBinding.root)
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
            setCanceledOnTouchOutside(false)
        }

        confirmationDialog.show()
    }
}