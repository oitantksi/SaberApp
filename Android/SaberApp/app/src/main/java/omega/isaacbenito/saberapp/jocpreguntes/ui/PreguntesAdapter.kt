package omega.isaacbenito.saberapp.jocpreguntes.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import omega.isaacbenito.saberapp.R
import omega.isaacbenito.saberapp.data.entities.PreguntaAmbResposta
import omega.isaacbenito.saberapp.databinding.PreguntaItemBinding
import omega.isaacbenito.saberapp.jocpreguntes.model.JocPreguntesViewModel

/**
 * Adaptador del llistat de preguntes per a mostrar en el [PreguntesFragment]
 *
 * @author Isaac Benito
 **/
class PreguntaAdapter(
    private val interaction: Interaction
) : ListAdapter<PreguntaAmbResposta, PreguntaAdapter.PreguntaVH>(
    PreguntaDiffCallback()
)  {

    class PreguntaVH(
        val context: Context,
        private val binding: PreguntaItemBinding,
        private val interaction: Interaction
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(preguntaAmbResposta: PreguntaAmbResposta) {
            val pregunta = preguntaAmbResposta.pregunta
            val resposta = preguntaAmbResposta.resposta

            binding.preguntaCardView.setCardBackgroundColor(ContextCompat.getColor(context,
                when (pregunta.materia) {
                    JocPreguntesViewModel.MATERIA_GEOGRAFIA -> R.color.materia_geografia
                    JocPreguntesViewModel.MATERIA_HISTORIA -> R.color.materia_història
                    JocPreguntesViewModel.MATERIA_ARTS -> R.color.materia_art
                    JocPreguntesViewModel.MATERIA_MATES -> R.color.materia_matematiques
                    else -> R.color.colorAccent
                }))

            binding.preguntaMateriaIcon.setImageDrawable(
                ContextCompat.getDrawable(context,
                    when (pregunta.materia) {
                        JocPreguntesViewModel.MATERIA_GEOGRAFIA -> R.drawable.materia_geografia_icon
                        JocPreguntesViewModel.MATERIA_HISTORIA -> R.drawable.materia_historia_icon
                        JocPreguntesViewModel.MATERIA_ARTS -> R.drawable.materia_arts_icon
                        JocPreguntesViewModel.MATERIA_MATES -> R.drawable.materia_mates_icon
                        else -> R.drawable.applogo
                    }))

            binding.preguntaText.text = pregunta.question

            if (resposta != null) {
                binding.resposta.visibility = View.VISIBLE

                binding.respostaText.text = when(resposta.resposta) {
                    1 -> pregunta.ans1
                    2 -> pregunta.ans2
                    3 -> pregunta.ans3
                    4 -> pregunta.ans4
                    else -> ""
                }

                binding.ansIcon.setImageDrawable(ContextCompat.getDrawable(context,
                    if (pregunta.right_ans == resposta.resposta) {
                        R.drawable.right_icon
                    } else {
                        R.drawable.wrong_icon
                    }
                ))
            }

            binding.root.setOnClickListener {
                interaction.onClickPregunta(adapterPosition, preguntaAmbResposta)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreguntaVH {
        return PreguntaVH(
            parent.context,
            PreguntaItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            interaction
        )
    }

    override fun onBindViewHolder(holder: PreguntaVH, position: Int) {
        val preguntaAmbResposta = getItem(position)
        holder.bind(preguntaAmbResposta)
    }

    interface Interaction {
        fun onClickPregunta(position: Int, preguntaAmbResposta: PreguntaAmbResposta)
    }
}

private class PreguntaDiffCallback : DiffUtil.ItemCallback<PreguntaAmbResposta>() {
    override fun areItemsTheSame(
        oldItem: PreguntaAmbResposta,
        newItem: PreguntaAmbResposta
    ): Boolean {
        return (oldItem.pregunta.id == newItem.pregunta.id)
    }

    override fun areContentsTheSame(
        oldItem: PreguntaAmbResposta,
        newItem: PreguntaAmbResposta
    ): Boolean {
        return oldItem == newItem
    }
}