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

package omega.isaacbenito.saberapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import omega.isaacbenito.saberapp.data.entities.*
import omega.isaacbenito.saberapp.data.local.database.dao.*

@Database(
    entities = [
        User::class,
        ProfilePicture::class,
        Centre::class,
        Materia::class,
        Pregunta::class,
        Resposta::class,
        Score::class
    ],
    version = 18, exportSchema = true
)
@TypeConverters(Converters::class)
abstract class SaberAppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun centreDao(): CentreDao
    abstract fun preguntaDao(): PreguntaDao
    abstract fun materiaDao(): MateriaDao
    abstract fun respostaDao(): RespostaDao
    abstract fun scoreDao(): ScoreDao
}
