package omega.isaacbenito.saberapp.data.entities

import androidx.room.Embedded
import androidx.room.Relation

/**
 * TODO
 *
 * @author Isaac Benito
 **/
data class UserWithPicture(
    @Embedded
    val user: User,
    @Relation(entity = ProfilePicture::class, parentColumn = "id", entityColumn = "userId")
    val profilePicture: ProfilePicture?
)
