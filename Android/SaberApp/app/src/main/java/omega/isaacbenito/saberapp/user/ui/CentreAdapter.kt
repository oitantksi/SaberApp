package omega.isaacbenito.saberapp.user.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import omega.isaacbenito.saberapp.data.entities.Centre
import omega.isaacbenito.saberapp.databinding.RegCentreItemBinding

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



