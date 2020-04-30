package omega.isaacbenito.saberapp.data.entities

import androidx.room.Embedded
import androidx.room.Relation

/**
 * TODO
 *
 * @author Isaac Benito
 **/
data class ScoreWithUserAndMateria(
    @Embedded
    val score: Score,
    @Relation(entity = Materia::class, parentColumn = "materiaId", entityColumn = "id")
    val materia: Materia,
    @Relation(entity = User::class, parentColumn = "userId", entityColumn = "id")
    val user: User
)
