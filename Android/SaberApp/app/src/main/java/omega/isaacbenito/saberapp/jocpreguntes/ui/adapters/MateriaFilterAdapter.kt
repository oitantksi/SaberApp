package omega.isaacbenito.saberapp.jocpreguntes.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import omega.isaacbenito.saberapp.data.entities.Materia
import omega.isaacbenito.saberapp.databinding.ItemMateriaChecklistBinding

/**
 * TODO
 *
 * @author Isaac Benito
 **/
class MateriaFilterAdapter(
    private val interaction: Interaction
) : ListAdapter<Pair<Materia, Boolean>, MateriaFilterAdapter.MateriaFilterVH>(
    MateriaFilterDiffCallback()
) {
    class MateriaFilterVH(
        private val binding: ItemMateriaChecklistBinding,
        private val interaction: Interaction
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Pair<Materia, Boolean>) {
            binding.materiaName.text = item.first.name
            binding.materiaCheckbox.isChecked = item.second
            binding.root.setOnClickListener {
                interaction.onClick(item.first)
                binding.materiaCheckbox.isChecked = !binding.materiaCheckbox.isChecked
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MateriaFilterVH {
        return MateriaFilterVH(
            ItemMateriaChecklistBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), interaction
        )
    }

    override fun onBindViewHolder(holder: MateriaFilterVH, position: Int) {
        holder.bind(getItem(position))
    }

    interface Interaction {
        fun onClick(materia: Materia)
    }
}

class MateriaFilterDiffCallback : DiffUtil.ItemCallback<Pair<Materia, Boolean>>() {
    override fun areItemsTheSame(
        oldItem: Pair<Materia, Boolean>,
        newItem: Pair<Materia, Boolean>
    ): Boolean {
        return (oldItem.first == newItem.first)
    }

    override fun areContentsTheSame(
        oldItem: Pair<Materia, Boolean>,
        newItem: Pair<Materia, Boolean>
    ): Boolean {
        return oldItem.second == newItem.second
    }
}
