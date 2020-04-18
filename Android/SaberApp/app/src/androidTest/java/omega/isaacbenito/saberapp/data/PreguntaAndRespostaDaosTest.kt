package omega.isaacbenito.saberapp.data

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import omega.isaacbenito.saberapp.data.entities.*
import omega.isaacbenito.saberapp.data.local.database.SaberAppDatabase
import omega.isaacbenito.saberapp.data.local.database.dao.PreguntaDao
import omega.isaacbenito.saberapp.data.local.database.dao.RespostaDao
import omega.isaacbenito.saberapp.utils.waitForValue
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.internal.util.collections.ListUtil
import java.io.IOException
import java.util.*

/**
 * Test per a provar la funcionalitat de les DAO's de les entitats [Pregunta] i [Resposta]
 *
 * @author Isaac Benito
 **/
class PreguntaAndRespostaDaosTest {
    private lateinit var db: SaberAppDatabase
    private lateinit var preguntaDao: PreguntaDao
    private lateinit var respostaDao: RespostaDao

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
        User(1L, "name", "surname", "nickname",
            "email@email.com", "centre"),
        User(2L, "name2", "surname2", "nickname2",
            "email2@email.com", "centre2"),
        User(3L, "name3", "surname3", "nickname3",
            "email3@email.com", "centre3"),
        User(4L, "name4", "surname4", "nickname4",
            "email4@email.com", "centre4")
    )

    @Before
    fun createDB() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, SaberAppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        preguntaDao = db.preguntaDao()
        respostaDao = db.respostaDao()
        db.userDao().save(users)
        db.materiaDao().save(materies)
    }

    @After
    @Throws(IOException::class)
    fun closeDB() {
        db.close()
    }

    @Test
    fun saveAndReadMultiplePregunta() {
        val preguntes = listOf(
            Pregunta(1L, "pregunta", "r1", "r2", "r3", "r4",
                1, cal.time, 1L),
            Pregunta(2L, "pregunta2", "r1", "r2", "r3", "r4",
                2, cal.time, 2L),
            Pregunta(3L, "pregunta3", "r1", "r2", "r3", "r4",
                3, cal.time, 3L),
            Pregunta(4L, "pregunta4", "r1", "r2", "r3", "r4",
                4, cal.time, 4L)
        )

        preguntaDao.save(preguntes)
        assertThat(preguntaDao.getPreguntes().waitForValue(), equalTo(preguntes))

    }

    @Test
    fun saveAndReadMultipleRespostesOnSamePregunta() {
        val pregunta = Pregunta(1L, "pregunta", "r1", "r2", "r3", "r4",
            1, cal.time, 1L)

        val respostes = listOf(
            Resposta(1L, 1L, 1, cal.time),
            Resposta(2L, 1L, 1, cal.time),
            Resposta(3L, 1L, 1, cal.time),
            Resposta(4L, 1L, 1, cal.time)
        )

        preguntaDao.save(pregunta)
        respostaDao.save(respostes)
        for (resposta in respostes) {
            assertThat(
                respostaDao.getResposta(resposta.userId, resposta.preguntaId),
                equalTo(resposta)
            )
        }
    }

    @Test
    fun saveAndReadMultipleRespostesFromSameUser() {
        val preguntes = listOf(
            Pregunta(1L, "pregunta", "r1", "r2", "r3", "r4",
                1, cal.time, 1L),
            Pregunta(2L, "pregunta2", "r1", "r2", "r3", "r4",
                2, cal.time, 2L),
            Pregunta(3L, "pregunta3", "r1", "r2", "r3", "r4",
                3, cal.time, 3L),
            Pregunta(4L, "pregunta4", "r1", "r2", "r3", "r4",
                4, cal.time, 4L)
        )

        val respostes = listOf(
            Resposta(1L, 1L, 1, cal.time),
            Resposta(1L, 2L, 1, cal.time),
            Resposta(1L, 3L, 1, cal.time),
            Resposta(1L, 4L, 1, cal.time)
        )

        preguntaDao.save(preguntes)
        respostaDao.save(respostes)
        for (resposta in respostes) {
            assertThat(
                respostaDao.getResposta(resposta.userId, resposta.preguntaId),
                equalTo(resposta)
            )
        }
    }

    @Test
    fun getRespostesByUserTest() {
        val preguntes = listOf(
            Pregunta(1L, "pregunta", "r1", "r2", "r3", "r4",
                1, cal.time, 1L),
            Pregunta(2L, "pregunta2", "r1", "r2", "r3", "r4",
                2, cal.time, 2L),
            Pregunta(3L, "pregunta3", "r1", "r2", "r3", "r4",
                3, cal.time, 3L),
            Pregunta(4L, "pregunta4", "r1", "r2", "r3", "r4",
                4, cal.time, 4L)
        )

        val respostes = listOf(
            Resposta(1L, 3L, 1, cal.time),
            Resposta(1L, 4L, 1, cal.time),
            Resposta(2L, 1L, 2, cal.time),
            Resposta(2L, 2L, 2, cal.time)
        )

        preguntaDao.save(preguntes)
        respostaDao.save(respostes)

        assertThat(
            respostaDao.getRespostesByUser(1L).waitForValue(),
            equalTo(respostes.filter { it.userId == 1L })
        )
    }

    @Test
    fun getPreguntesAmbRespostaByUserTest() {
        val preguntes = listOf(
            Pregunta(1L, "pregunta", "r1", "r2", "r3", "r4",
                    1, cal.time, 1L),
            Pregunta(2L, "pregunta", "r1", "r2", "r3", "r4",
                    1, cal.time, 1L),
            Pregunta(3L, "pregunta", "r1", "r2", "r3", "r4",
                    1, cal.time, 1L),
            Pregunta(4L, "pregunta", "r1", "r2", "r3", "r4",
                1, cal.time, 1L)
        )

        val respostes = listOf(
            Resposta(1L, 3L, 1, cal.time),
            Resposta(1L, 4L, 1, cal.time),
            Resposta(2L, 1L, 2, cal.time),
            Resposta(2L, 2L, 2, cal.time)
        )

        preguntaDao.save(preguntes)
        respostaDao.save(respostes)

        val expectedUser1 = preguntes.map { p ->
            PreguntaAmbResposta(
                p,
                respostes.firstOrNull { r ->
                    r.preguntaId == p.id && r.userId == 1L
                })
        }
        val dataUser1 = preguntaDao.getPreguntesAmbRespostaByUser(1L).waitForValue()
        assertThat(dataUser1, equalTo(expectedUser1))

//        val expectedUser2 = preguntes.map { p ->
//            PreguntaAmbResposta(
//                p,
//                respostes.firstOrNull { r ->
//                    r.preguntaId == p.id && r.userId == 1L
//                })
//        }
//        val dataUser2 = preguntaDao.getPreguntesAmbRespostaByUser(2L).waitForValue()
//        assertThat(dataUser2, equalTo(expectedUser2))
    }
}