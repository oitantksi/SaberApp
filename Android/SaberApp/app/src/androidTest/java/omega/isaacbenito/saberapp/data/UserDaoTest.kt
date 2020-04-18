package omega.isaacbenito.saberapp.data

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import omega.isaacbenito.saberapp.data.entities.User
import omega.isaacbenito.saberapp.data.local.database.SaberAppDatabase
import omega.isaacbenito.saberapp.data.local.database.dao.UserDao
import omega.isaacbenito.saberapp.utils.waitForValue
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException

class UserDaoTest {
    private lateinit var db: SaberAppDatabase
    private lateinit var userDao: UserDao

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDB() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, SaberAppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        userDao = db.userDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDB() {
        db.close()
    }

    @Test
    fun writeAndRead() {
        val user = User(1L, "name", "surname", "nickname",
            "email@email.com", "centre")
        val id = userDao.insert(user)
        assertThat(userDao.getUserId(user.email), equalTo(id))
        assertThat(userDao.get(user.email).waitForValue(), equalTo(user))
    }

    @Test
    fun writeUpdateAndRead() {
        val user = User(1L, "name", "surname", "nickname",
            "email@email.com", "centre")

        userDao.insert(user)
        user.email = "nouemail@email.com"
        userDao.update(user)
        assertThat(userDao.get(user.email).waitForValue(), equalTo(user))
        }

    @Test
    fun writeAndReadMultiple() {
        val users = listOf(
            User(1L, "name", "surname", "nickname",
                "email@email.com", "centre"),
            User(2L, "name2", "surname2", "nickname2",
                "email2@email.com", "centre2"),
            User(3L, "name3", "surname3", "nickname3",
                "email3@email.com", "centre3"),
            User(4L, "name4", "surname4", "nickname4",
                "email4@email.com", "centre4")
        )

        userDao.insert(users)
        for (user in users) {
            assertThat(userDao.get(user.email).waitForValue(), equalTo(user))
        }
    }

    @Test
    fun writeUpdateAndReadMultiple() {
        val users = listOf(
            User(1L, "name", "surname", "nickname",
                "email@email.com", "centre"),
            User(2L, "name2", "surname2", "nickname2",
                "email2@email.com", "centre2"),
            User(3L, "name3", "surname3", "nickname3",
                "email3@email.com", "centre3"),
            User(4L, "name4", "surname4", "nickname4",
                "email4@email.com", "centre4")
        )

        userDao.insert(users)
        users[0].email = "nouemail@email.com"
        users[1].email = "nouemail2@email.com"
        users[2].email = "nouemail3@email.com"
        users[3].email = "nouemail4@email.com"
        userDao.update(users)
        for (user in users) {
            assertThat(userDao.get(user.email).waitForValue(), equalTo(user))
        }
    }

    @Test
    fun saveAndRead() {
        val user = User(1L, "name", "surname", "nickname",
            "email@email.com", "centre")

        userDao.save(user)
        assertThat(userDao.get(user.email).waitForValue(), equalTo(user))
        user.email = "nouemail@email.com"
        userDao.save(user)
        assertThat(userDao.get(user.email).waitForValue(), equalTo(user))
    }

    @Test
    fun saveAndReadMultiple() {
        val users = listOf(
            User(1L, "name", "surname", "nickname",
                "email@email.com", "centre"),
            User(2L, "name2", "surname2", "nickname2",
                "email2@email.com", "centre2"),
            User(3L, "name3", "surname3", "nickname3",
                "email3@email.com", "centre3"),
            User(4L, "name4", "surname4", "nickname4",
                "email4@email.com", "centre4")
        )

        userDao.save(users)
        users[0].email = "nouemail@email.com"
        users[1].email = "nouemail2@email.com"
        users[2].email = "nouemail3@email.com"
        users[3].email = "nouemail4@email.com"
        userDao.save(users)
        for (user in users) {
            assertThat(userDao.get(user.email).waitForValue(), equalTo(user))
        }
    }

    @Test
    fun getAllTest() {
        val users = listOf(
            User(1L, "name", "surname", "nickname",
                "email@email.com", "centre"),
            User(2L, "name2", "surname2", "nickname2",
                "email2@email.com", "centre2"),
            User(3L, "name3", "surname3", "nickname3",
                "email3@email.com", "centre3"),
            User(4L, "name4", "surname4", "nickname4",
                "email4@email.com", "centre4")
        )

        userDao.save(users)
        val dbUsers = userDao.getAll().waitForValue()
        assertThat(dbUsers?.size, equalTo(users.size))
        assertThat(dbUsers, equalTo(users))
    }

}