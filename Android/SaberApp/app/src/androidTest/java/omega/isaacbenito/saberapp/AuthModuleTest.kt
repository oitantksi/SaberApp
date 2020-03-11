package omega.isaacbenito.saberapp

import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.view.WindowManager
import androidx.navigation.NavDeepLinkBuilder
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.Root
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import junit.framework.TestCase.fail
import omega.isaacbenito.saberapp.authentication.ui.AuthActivity
import omega.isaacbenito.saberapp.authentication.ui.CentreAdapter
import omega.isaacbenito.saberapp.authentication.ui.RegCentreFragment
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.any
import org.hamcrest.TypeSafeMatcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@LargeTest
class AuthModuleTest {

    private lateinit var userMail: String
    private lateinit var password: String

//    @get:Rule
//    var activityRule: ActivityTestRule<AuthActivity> =
//        ActivityTestRule(AuthActivity::class.java)

    @get:Rule
    var activityRule = ActivityTestRule(AuthActivity::class.java)

    private fun launchFragment(destinationId: Int,
                               argBundle: Bundle? = null) {
        val intent = NavDeepLinkBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext)
            .setGraph(R.navigation.auth_navigation)
            .setComponentName(AuthActivity::class.java)
            .setDestination(destinationId)
            .createTaskStackBuilder().intents[0]
        activityRule.launchActivity(intent)
    }

    @Before
    fun launchLoginFragment() {

    }

    @Test
    fun loginFragment_login_wrongCredentials_toastShown() {
        launchFragment(R.id.loginFragment)

        userMail = "isaac.benito@omega.com"
        password = "omega"

        //Type mail
        onView(withId(R.id.accountMail)).perform(
            typeText(userMail)
        )

        //Type password
        onView(withId(R.id.accountPassword)).perform(
            typeText(password),
            closeSoftKeyboard()
        )

        // Click login button
        onView(withId(R.id.submitLogin)).perform(click())

        // Check Toast is displayed
        onView(withText(R.string.wrong_credentials)).inRoot(ToastMatcher())
            .check(matches(isDisplayed()))

    }

    @Test
    fun loginFragment_login_rightCredentials() {
        launchFragment(R.id.loginFragment)

        userMail = "ramon@omega.com"
        password = "omega"

        //Type mail
        onView(withId(R.id.accountMail)).perform(
            typeText(userMail),
            closeSoftKeyboard()
        )

        //Type password
        onView(withId(R.id.accountPassword)).perform(
            typeText(password),
            closeSoftKeyboard()
        )

        // Click login button
        onView(withId(R.id.submitLogin)).perform(click())

        if (waitForView(R.id.userFragment, 2000)) {
            fail()
        }

    }

    @Test
    fun register_user() {
        val name = "Name"
        val surname = "Surname"
        val nickname = "Nickname"
        userMail = "email@email.com"
        password = "password"
        val centre =  1
        val role = 'A'

        launchFragment(R.id.regDataFragment)

        onView(withId(R.id.name)).perform(
            typeText(name),
            closeSoftKeyboard()
        )

        onView(withId(R.id.surname)).perform(
            typeText(surname),
            closeSoftKeyboard()
        )

        onView(withId(R.id.nickname)).perform(
            typeText(nickname),
            closeSoftKeyboard()
        )

        onView(withId(R.id.accountMail)).perform(
            typeText(userMail),
            closeSoftKeyboard()
        )

        onView(withId(R.id.accountPassword)).perform(
            typeText(password),
            closeSoftKeyboard()
        )

        onView(withId(R.id.accountPassword_repeat)).perform(
            typeText(password),
            closeSoftKeyboard()
        )

        onView(withId(R.id.submitData)).perform(click())

        onView(withId(R.id.centre_list)).check(matches(isDisplayed()))

        onView(withId(R.id.centre_list)).perform(
            RecyclerViewActions.actionOnItemAtPosition<CentreAdapter.CentreVH>(
                centre,
                click()
            )
        )


    }

    @After
    fun finishActivity() {
        activityRule.finishActivity()
    }

    protected fun waitForView(id: Int, timeout: Int): Boolean {
        val endTime = SystemClock.uptimeMillis() + timeout
        while (SystemClock.uptimeMillis() <= endTime) {
            try {
                onView(withId(id)).check(matches(isDisplayed()))
                return true
            } catch (e: NoMatchingViewException) {}
        }
        return false
    }


}

class ToastMatcher : TypeSafeMatcher<Root>() {

    override fun matchesSafely(item: Root?): Boolean {
        val type = item?.windowLayoutParams?.get()?.type
        if (type == WindowManager.LayoutParams.TYPE_TOAST) {
            val windowToken = item.decorView.windowToken
            val appToken = item.decorView.applicationWindowToken
            if ( windowToken == appToken) {
                return true
            }
        }
        return false
    }

    override fun describeTo(description: Description?) {
        description?.appendText("is toast");
    }
}