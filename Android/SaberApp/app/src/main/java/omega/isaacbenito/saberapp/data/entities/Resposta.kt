package omega.isaacbenito.saberapp.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import com.google.gson.annotations.SerializedName
import java.sql.Timestamp
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * Entitat que defineix la taula "respostes" de la base de dades
 *
 * @author Isaac Benito
 **/
@Entity(tableName = "respostes",
    primaryKeys = ["userId", "preguntaId"],
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Pregunta::class,
            parentColumns = ["id"],
            childColumns = ["preguntaId"],
            onDelete = ForeignKey.CASCADE
        )
    ])
data class Resposta (
    @SerializedName("userId")
    val userId: Long,
    @SerializedName("preguntaId")
    val preguntaId: Long,
    @SerializedName("respuesta")
    val resposta: Int,
    @SerializedName("fechaRespuesta")
    val dataResposta: Date
) {

    class Dto(
        @SerializedName("userId")
        val userId: Long,
        @SerializedName("preguntaId")
        val preguntaId: Long,
        @SerializedName("respuesta")
        val resposta: Long,
        @SerializedName("fechaRespuesta")
        val dataResposta: String
    )

    fun getDto(): Dto {
        return Dto(
            this.userId, this.preguntaId, this.resposta.toLong(), dateToString(this.dataResposta)
        )
    }

    fun dateToString(date: Date) : String {
        val datePattern = "YYYY-MM-dd'T'HH:mm:ss.SSSZ"
        val df = SimpleDateFormat(datePattern)
        return df.format(date)
    }
}

