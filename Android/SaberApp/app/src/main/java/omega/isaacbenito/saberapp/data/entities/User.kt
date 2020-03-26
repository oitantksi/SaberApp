/*
 * This file is part of SaberApp.
 *
 *     SaberApp is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     SaberApp is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with SaberApp.  If not, see <https://www.gnu.org/licenses/>.
 */

package omega.isaacbenito.saberapp.data.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "users", indices = [Index(value = ["email"], unique = true)])
data class User(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    var name: String,
    @SerializedName("cognom")
    var surname: String,
    var nickname: String,
    var email: String,
    @SerializedName("center")
    var centre: String,
    @SerializedName("rol")
    var role: Char = 'A'
) {

    constructor(userString: String) : this(
        Regex("(?<=id:)(.*?)(?=,)").find(userString)?.value?.toLong()
            ?: throw IllegalArgumentException(),
        Regex("(?<=,name:)(.*?)(?=,)").find(userString)?.value ?: throw IllegalArgumentException(),
        Regex("(?<=,surname:)(.*?)(?=,)").find(userString)?.value
            ?: throw IllegalArgumentException(),
        Regex("(?<=,nickname:)(.*?)(?=,)").find(userString)?.value
            ?: throw IllegalArgumentException(),
        Regex("(?<=,email:)(.*?)(?=,)").find(userString)?.value ?: throw IllegalArgumentException(),
        Regex("(?<=,centre:)(.*?)(?=,)").find(userString)?.value
            ?: throw IllegalArgumentException(),
        Regex("(?<=,role:)(.*?)(?=})").find(userString)?.value?.toCharArray()?.get(0)
            ?: throw IllegalArgumentException()
    )

    override fun toString(): String {
        return "User{id:$id,name:$name,surname:$surname,nickname:$nickname,email:$email,centre:$centre,role:$role}"
    }
}
