package omega.isaacbenito.saberapp.data.local.database

import android.net.Uri
import androidx.room.TypeConverter
import java.util.*

/**
 * Converitdors per a aquells objectes que no es poden desar a la base de dades
 *
 * @author Isaac Benito
 **/
class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? = value?.let { Date(it) }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? = date?.time?.toLong()

    @TypeConverter
    fun uriToString(uri: Uri?): String? = uri.toString()

    @TypeConverter
    fun uriFromString(string: String?): Uri? = Uri.parse(string)
}