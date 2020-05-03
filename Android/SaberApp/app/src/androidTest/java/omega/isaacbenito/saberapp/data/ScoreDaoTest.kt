package omega.isaacbenito.saberapp.data

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import omega.isaacbenito.saberapp.data.entities.Materia
import omega.isaacbenito.saberapp.data.entities.Score
import omega.isaacbenito.saberapp.data.entities.User
import omega.isaacbenito.saberapp.data.local.database.SaberAppDatabase
import omega.isaacbenito.saberapp.data.local.database.dao.ScoreDao
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException
import java.util.*

/**
 * Test per a provar la funcionalitat de la DAO de la entitat [Score]
 *
 * @author Isaac Benito
 **/
class ScoreDaoTest {
    private lateinit var db: SaberAppDatabase
    private lateinit var scoreDao: ScoreDao

    private val cal = Calendar.getInstance()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val materies = listOf(
        Materia(1L, "materia1"),
        Materia(2L, "materia2"),
        Materia(3L, "materia3"),
        Materia(4L, "materia4")
    )

    private val users = listOf(
        User(
            1L, "name", "surname", "nickname",
            "email@email.com", "centre"
        ),
        User(
            2L, "name2", "surname2", "nickname2",
            "email2@email.com", "centre2"
        ),
        User(
            3L, "name3", "surname3", "nickname3",
            "email3@email.com", "centre3"
        ),
        User(
            4L, "name4", "surname4", "nickname4",
            "email4@email.com", "centre4"
        )
    )

    @Before
    fun createDB() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, SaberAppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        scoreDao = db.scoreDao()
        db.userDao().save(users)
        db.materiaDao().save(materies)
    }

    @After
    @Throws(IOException::class)
    fun closeDB() {
        db.close()
    }

    @Test
    fun saveAndReadMultipleScore() {
        val scores = listOf(
            Score(1, 1, 3),
            Score(2, 1, 3),
            Score(3, 1, 3),
            Score(4, 1, 3),
            Score(4, 2, 3)
        )

        scoreDao.save(scores)
//        assertThat<List<Score>>(scoreDao.getScores().waitForValue(), equalTo(scores))

    }
}
