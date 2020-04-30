package omega.isaacbenito.saberapp.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import com.google.gson.annotations.SerializedName

/**
 * TODO
 *
 * @author Isaac Benito
 **/
@Entity(
    tableName = "scores",
    primaryKeys = ["userId", "materiaId"],
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Materia::class,
            parentColumns = ["id"],
            childColumns = ["materiaId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Score(
    val userId: Long,
    val materiaId: Long,
    val score: Int
) {

    companion object {
        fun fromDto(dto: Dto): Score = Score(dto.id.userId, dto.id.materiaId, dto.score)
    }

    data class ScoreId(
        @SerializedName("idAlumno")
        val userId: Long,
        @SerializedName("idMateria")
        val materiaId: Long
    )

    data class Dto(
        @SerializedName("puntuacionId")
        val id: ScoreId,
        @SerializedName("puntos")
        val score: Int
    )

}