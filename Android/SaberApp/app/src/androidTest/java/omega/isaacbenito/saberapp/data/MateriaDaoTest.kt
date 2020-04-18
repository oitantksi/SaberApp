package omega.isaacbenito.saberapp.data

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import omega.isaacbenito.saberapp.data.entities.Materia
import omega.isaacbenito.saberapp.data.local.database.SaberAppDatabase
import omega.isaacbenito.saberapp.data.local.database.dao.MateriaDao
import omega.isaacbenito.saberapp.utils.waitForValue
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException

/**
 * Test per a provar la funcionalitat de la DAO de l'entitat [Materia]
 *
 * @author Isaac Benito
 **/
class MateriaDaoTest {
    private lateinit var db: SaberAppDatabase
    private lateinit var materiaDao: MateriaDao

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDB() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, SaberAppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        materiaDao = db.materiaDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDB() {
        db.close()
    }

    @Test
    fun writeAndReadMultiple() {
        val materies = listOf(
            Materia(1L, "materia1"),
            Materia(2L, "materia2"),
            Materia(3L, "materia3"),
            Materia(4L, "materia4")
        )

        materiaDao.save(materies)
        assertThat(materiaDao.getMateries().waitForValue(), equalTo(materies))
        }
}