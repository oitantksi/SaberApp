package omega.isaacbenito.saberapp.jocpreguntes.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import omega.isaacbenito.saberapp.R
import omega.isaacbenito.saberapp.data.entities.Materia
import omega.isaacbenito.saberapp.databinding.ItemMateriaScoreBinding
import omega.isaacbenito.saberapp.jocpreguntes.model.JocPreguntesVM

class MateriaScoreAdapter(
    val interaction: Interaction
) : ListAdapter<Triple<Materia, Int, Int>, MateriaScoreAdapter.MateriaScoreVH>(
    MateriaScoreDiffCallback()
) {


    class MateriaScoreVH(
        val binding: ItemMateriaScoreBinding,
        val context: Context,
        val interaction: Interaction
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(materia: Materia, score: Int, classification: Int) {
            binding.scoreMateriaValue.text = score.toString()

            binding.materiaScoreCard.setCardBackgroundColor(
                ContextCompat.getColor(
                    context,
                    when (materia.id) {
                        0L -> R.color.colorPrimary
                        JocPreguntesVM.MATERIA_GEOGRAFIA -> R.color.materia_geografia
                        JocPreguntesVM.MATERIA_HISTORIA -> R.color.materia_història
                        JocPreguntesVM.MATERIA_ARTS -> R.color.materia_art
                        JocPreguntesVM.MATERIA_MATES -> R.color.materia_matematiques
                        else -> R.color.colorAccent
                    }
                )
            )

            binding.scoreMateriaIcon.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    when (materia.id) {
                        0L -> R.drawable.app_icon
                        JocPreguntesVM.MATERIA_GEOGRAFIA -> R.drawable.materia_geografia_icon
                        JocPreguntesVM.MATERIA_HISTORIA -> R.drawable.materia_historia_icon
                        JocPreguntesVM.MATERIA_ARTS -> R.drawable.materia_arts_icon
                        JocPreguntesVM.MATERIA_MATES -> R.drawable.materia_mates_icon
                        else -> R.drawable.applogo
                    }
                )
            )

            if (classification in 0..2) {
                binding.scoreTrophy.let {
                    it.visibility = View.VISIBLE
                    it.setImageDrawable(
                        ContextCompat.getDrawable(
                            context,
                            when (classification) {
                                0 -> R.drawable.score_gold
                                1 -> R.drawable.score_silver
                                2 -> R.drawable.score_bronze
                                else -> 0
                            }
                        )
                    )
                }
            }

            binding.root.setOnClickListener { interaction.onSelectMateria(materia.id) }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MateriaScoreVH {
        return MateriaScoreVH(
            ItemMateriaScoreBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            parent.context, interaction
        )
    }

    override fun onBindViewHolder(holder: MateriaScoreVH, position: Int) {
        val item = getItem(position)
        holder.bind(item.first, item.second, item.third)
    }

    interface Interaction {
        fun onSelectMateria(materiaId: Long)
    }
}

class MateriaScoreDiffCallback : DiffUtil.ItemCallback<Triple<Materia, Int, Int>>() {
    override fun areItemsTheSame(
        oldItem: Triple<Materia, Int, Int>,
        newItem: Triple<Materia, Int, Int>
    ): Boolean {
        return oldItem.first.id == newItem.first.id
    }

    override fun areContentsTheSame(
        oldItem: Triple<Materia, Int, Int>,
        newItem: Triple<Materia, Int, Int>
    ): Boolean {
        return oldItem == newItem
    }
}
