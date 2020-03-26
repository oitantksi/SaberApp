package omega.isaacbenito.saberapp.data.repos

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.work.testing.TestListenableWorkerBuilder
import omega.isaacbenito.saberapp.utils.NetworkUtils
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UserRepositoryTest {

    private lateinit var context: Context

    @Mock
    private lateinit var networkUtilsMock: NetworkUtils

    @Before
    private fun setup() {
        context = ApplicationProvider.getApplicationContext()
    }

    private fun testEditProfileWorker() {
        `when`(networkUtilsMock.isNetworkConnected).thenReturn(false)

        val worker = TestListenableWorkerBuilder<>(context)
    }

}