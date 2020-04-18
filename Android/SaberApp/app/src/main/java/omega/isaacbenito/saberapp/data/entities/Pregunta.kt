package omega.isaacbenito.saberapp.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Entitat que defineix la taula "preguntes" de la base de dades
 *
 * @author Isaac Benito
 **/
@Entity(tableName = "preguntes",
    foreignKeys = [
        ForeignKey(
            entity = Materia::class,
            parentColumns = ["id"],
            childColumns = ["materia"],
            onDelete = ForeignKey.RESTRICT)])
data class Pregunta (
    @PrimaryKey
    val id: Long,
    val question: String,
    val ans1: String,
    val ans2: String,
    val ans3: String,
    val ans4: String,
    val right_ans: Int,
    val date: Date,
    val materia: Long
) {

    class ServerQuest(
        val id: Long,
        @SerializedName("pregunta")
        val question: String,
        @SerializedName("respuesta1")
        val ans1: String,
        @SerializedName("respuesta2")
        val ans2: String,
        @SerializedName("respuesta3")
        val ans3: String,
        @SerializedName("respuesta4")
        val ans4: String,
        @SerializedName("respuesta_correcta")
        val right_ans: Int,
        @SerializedName("fecha_aparicion")
        val date: Date,
        @SerializedName("materia")
        val materia: Materia
    ) {
        fun getPregunta(): Pregunta =
            Pregunta(
                id,
                question,
                ans1,
                ans2,
                ans3,
                ans4,
                right_ans,
                date,
                materia.id
            )
    }
}