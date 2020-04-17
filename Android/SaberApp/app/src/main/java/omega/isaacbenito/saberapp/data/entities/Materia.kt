package omega.isaacbenito.saberapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Entitat que defineix la taula "materies" de la base de dades
 *
 * @author Isaac Benito
 **/
@Entity(tableName = "materies")
data class Materia (
    @PrimaryKey
    val id: Long,
    @SerializedName("nombre")
    val name: String
){
}