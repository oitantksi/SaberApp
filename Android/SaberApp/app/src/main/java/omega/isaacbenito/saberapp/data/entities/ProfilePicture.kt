package omega.isaacbenito.saberapp.data.entities

import android.net.Uri
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * TODO
 *
 * @author Isaac Benito
 **/
@Entity(
    tableName = "profile_pictures",
    foreignKeys = [ForeignKey(
        entity = User::class, parentColumns = ["id"],
        childColumns = ["userId"], onDelete = ForeignKey.CASCADE
    )]
)
data class ProfilePicture(
    @PrimaryKey
    val userId: Long,
    val pictureUri: Uri
)
