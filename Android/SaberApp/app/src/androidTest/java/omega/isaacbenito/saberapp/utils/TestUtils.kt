package omega.isaacbenito.saberapp.utils

import android.content.Intent
import android.os.SystemClock
import android.view.WindowManager
import androidx.navigation.NavDeepLinkBuilder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.Root
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import omega.isaacbenito.saberapp.R
import omega.isaacbenito.saberapp.authentication.ui.AuthActivity
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

class TestUtils {

    companion object {

        fun getFragmentIntent(destinationId: Int): Intent {
            return NavDeepLinkBuilder(
                InstrumentationRegistry.getInstrumentation().targetContext
            )
                .setGraph(R.navigation.auth_navigation)
                .setComponentName(AuthActivity::class.java)
                .setDestination(destinationId)
                .createTaskStackBuilder().intents[0]
        }

        fun waitForViewWithId(id: Int, timeout: Int): Boolean {
            val endTime = SystemClock.uptimeMillis() + timeout
            while (SystemClock.uptimeMillis() <= endTime) {
                try {
                    onView(withId(id)).check(matches(isDisplayed()))
                    return true
                } catch (e: NoMatchingViewException) {
                }
            }
            return false
        }

        fun waitForViewWithText(text: String, timeout: Int): Boolean {
            val endTime = SystemClock.uptimeMillis() + timeout
            while (SystemClock.uptimeMillis() <= endTime) {
                try {
                    onView(withText(text)).check(matches(isDisplayed()))
                    return true
                } catch (e: NoMatchingViewException) {
                }
            }
            return false
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