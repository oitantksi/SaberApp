package omega.isaacbenito.saberapp.utils

import android.app.Activity
import android.os.SystemClock
import android.view.WindowManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.Root
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class TestUtils {

    companion object {

        fun waitFor(action: () -> Unit, timeout: Int) {
            val endTime = SystemClock.uptimeMillis() + timeout
            var catchedException = Exception()
            while (SystemClock.uptimeMillis() <= endTime) {
                try {
                    action()
                    return
                } catch (e: Exception) {
                    catchedException = e
                }
            }

            throw catchedException
        }

        fun waitForRecyclerViewToFill(activity: Activity, recyclerViewId: Int, timeout: Int) {

            val recyclerView: RecyclerView
            try {
                recyclerView = activity.findViewById(recyclerViewId)
            } catch (e: NoMatchingViewException) {
                throw e
            }

            waitFor(
                {if (recyclerView.childCount == 0) {throw Exception("No childs found")}},
                timeout
            )
        }
    }

    class ToastMatcher : TypeSafeMatcher<Root>() {

        @SuppressWarnings
        override fun matchesSafely(item: Root?): Boolean {
            val type = item?.windowLayoutParams?.get()?.type
            if (type == WindowManager.LayoutParams.TYPE_TOAST) {
                val windowToken = item.decorView.windowToken
                val appToken = item.decorView.applicationWindowToken
                if (windowToken == appToken) {
                    return true
                }
            }
            return false
        }

        override fun describeTo(description: Description?) {
            description?.appendText("is toast")
        }
    }
}

@Throws(InterruptedException::class)
fun <T> LiveData<T>.waitForValue(): T {
    val data = arrayOfNulls<Any>(1)
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(o: T?) {
            data[0] = o
            latch.countDown()
            this@waitForValue.removeObserver(this)
        }
    }
    this.observeForever(observer)
    latch.await(2, TimeUnit.SECONDS)

    return data[0] as T
}