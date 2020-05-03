package omega.isaacbenito.saberapp.jocpreguntes.ui.adapters

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import omega.isaacbenito.saberapp.R
import omega.isaacbenito.saberapp.data.entities.UserWithPicture
import omega.isaacbenito.saberapp.databinding.ItemPlayerScoreBinding
import timber.log.Timber

class ClassificationAdapter(val currentUserId: Long) :
    ListAdapter<Triple<UserWithPicture, Int, Int>, ClassificationAdapter.PlayerScoreVH>(
        PlayerScoreDiffCallback()
    ) {
    class PlayerScoreVH(
        val binding: ItemPlayerScoreBinding,
        val currentUserId: Long,
        val context: Context
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(player: UserWithPicture, score: Int, position: Int) {
            binding.player = player.user
            if (player.profilePicture != null) {
                binding.playerPicture.setImageURI(player.profilePicture.pictureUri)
            }
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

            if (player.user.id == currentUserId) {
                val background = binding.root.background

                if (background is GradientDrawable) {
                    Timber.d("GradientDrawable")
                    background.mutate()
                    background.colors = intArrayOf(
                        ContextCompat.getColor(context, R.color.colorPrimaryLigth),
                        ContextCompat.getColor(context, R.color.colorAccent)
                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerScoreVH {
        return PlayerScoreVH(
            ItemPlayerScoreBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            currentUserId,
            parent.context
        )
    }

    override fun onBindViewHolder(holder: PlayerScoreVH, position: Int) {
        val data = getItem(position)
        holder.bind(data.first, data.second, data.third)
    }
}

class PlayerScoreDiffCallback : DiffUtil.ItemCallback<Triple<UserWithPicture, Int, Int>>() {
    override fun areItemsTheSame(
        oldItem: Triple<UserWithPicture, Int, Int>,
        newItem: Triple<UserWithPicture, Int, Int>
    ): Boolean {
        return oldItem.first.user.id == newItem.first.user.id
    }

    override fun areContentsTheSame(
        oldItem: Triple<UserWithPicture, Int, Int>,
        newItem: Triple<UserWithPicture, Int, Int>
    ): Boolean {
        return oldItem == newItem
    }
}
