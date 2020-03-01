package omega.isaacbenito.saberapp.repo

import omega.isaacbenito.saberapp.database.dao.UserDao
import java.util.concurrent.Executor

class UserRepository (
    private val executor: Executor,
    private val userDao: UserDao
){

    fun getUser(userId: Long) {
        userDao.get(userId)
    }
}