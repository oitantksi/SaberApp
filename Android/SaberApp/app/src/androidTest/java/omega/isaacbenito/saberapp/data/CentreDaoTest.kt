package omega.isaacbenito.saberapp.data

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import omega.isaacbenito.saberapp.data.entities.Centre
import omega.isaacbenito.saberapp.data.local.database.SaberAppDatabase
import omega.isaacbenito.saberapp.data.local.database.dao.CentreDao
import omega.isaacbenito.saberapp.utils.waitForValue
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

/**
 * Test per a provar la funcionalitat de la DAO de l'entitat [Centre]
 *
 * @author Isaac Benito
 **/
@RunWith(AndroidJUnit4::class)
class CentreDaoTest {
    private lateinit var db: SaberAppDatabase
    private lateinit var centreDao: CentreDao

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDB() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, SaberAppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        centreDao = db.centreDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDB() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeAndReadMultiple() {
        val centres = listOf(
            Centre(1L, "centre"),
            Centre(2L, "centre2"),
            Centre(3L, "centre3"),
            Centre(4L, "centre4")
        )
        centreDao.updateAllCentres(centres)
        val allCentres = centreDao.getAllCentres().waitForValue()
        assertThat(allCentres, equalTo(centres))
    }
}
