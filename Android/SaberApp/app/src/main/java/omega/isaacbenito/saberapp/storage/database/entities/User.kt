package omega.isaacbenito.saberapp.storage.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User (
    @PrimaryKey val id: Long,
    var name: String,
    var surname: String,
    var nickname: String,
    var email: String,
    var school: String,
    var role: Char
)