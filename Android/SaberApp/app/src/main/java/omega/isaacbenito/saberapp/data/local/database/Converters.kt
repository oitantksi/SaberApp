package omega.isaacbenito.saberapp.data.local.database

import androidx.room.TypeConverter
import java.util.*

/**
 * Converitdors per a aquells objectes que no es poden desar a la base de dades
 *
 * @author Isaac Benito
 **/
class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }
}