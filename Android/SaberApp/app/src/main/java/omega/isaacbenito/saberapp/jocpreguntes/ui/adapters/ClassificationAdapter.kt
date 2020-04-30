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
import omega.isaacbenito.saberapp.data.entities.User
import omega.isaacbenito.saberapp.databinding.ItemPlayerScoreBinding

class ClassificationAdapter :
    ListAdapter<Triple<User, Int, Int>, ClassificationAdapter.PlayerScoreVH>(
        PlayerScoreDiffCallback()
    ) {
    class PlayerScoreVH(
        val binding: ItemPlayerScoreBinding,
        val context: Context
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(player: User, score: Int, position: Int) {
            binding.player = player
            binding.playerScore.text = score.toString()

            if (position in (0..2)) {
                binding.playerTrophy.visibility = View.VISIBLE
                binding.playerTrophy.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        when (position) {
                            0 -> R.drawable.score_gold
                            1 -> R.drawable.score_silver
                            2 -> R.drawable.score_bronze
                            else -> throw IllegalStateException()
                        }
                    )
                )
            } else {
                binding.playerTrophy.visibility = View.INVISIBLE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerScoreVH {
        return PlayerScoreVH(
            ItemPlayerScoreBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            parent.context
        )
    }

    override fun onBindViewHolder(holder: PlayerScoreVH, position: Int) {
        val data = getItem(position)
        holder.bind(data.first, data.second, data.third)
    }
}

class PlayerScoreDiffCallback : DiffUtil.ItemCallback<Triple<User, Int, Int>>() {
    override fun areItemsTheSame(
        oldItem: Triple<User, Int, Int>,
        newItem: Triple<User, Int, Int>
    ): Boolean {
        return oldItem.first == newItem.first
    }

    override fun areContentsTheSame(
        oldItem: Triple<User, Int, Int>,
        newItem: Triple<User, Int, Int>
    ): Boolean {
        return oldItem == newItem
    }
}
