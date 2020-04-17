package omega.isaacbenito.saberapp.data.entities

import androidx.room.Embedded
import androidx.room.Relation

/**
 * POJO per a unir els objectes [Pregunta] i [Resposta] en una crida a la base de dades
 *
 * @author Isaac Benito
 **/
data class PreguntaAmbResposta (
    @Embedded
    val pregunta: Pregunta,
    @Relation(parentColumn = "id", entityColumn = "preguntaId", entity = Resposta::class)
    val resposta: Resposta?
)