package omega.isaacbenito.saberapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import junit.framework.TestCase.fail
import omega.isaacbenito.saberapp.authentication.ui.AuthActivity
import omega.isaacbenito.saberapp.utils.TestUtils.Companion.getFragmentIntent
import omega.isaacbenito.saberapp.utils.TestUtils.Companion.waitForViewWithId
import org.junit.After
import org.junit.Rule
import org.junit.Test

class LoginFragmentTest {

    private val rightUserMail = "isaac@omega.com"
    private val rightUserPassword = "Abr@kd4bra!"

    @get:Rule
    private val activityRule = ActivityTestRule(AuthActivity::class.java)

    private val testedFragment = R.id.loginFragment

    private fun launchFragment() {
        activityRule.launchActivity(getFragmentIntent(testedFragment))
    }

    private fun setCredentials(userMail: String, password: String) {
        //Type mail
        onView(withId(R.id.login_account_mail)).perform(
            typeText(userMail)
        )

        //Type password
        onView(withId(R.id.login_account_password)).perform(
            typeText(password),
            closeSoftKeyboard()
        )

        // Click login button
        onView(withId(R.id.submitLogin)).perform(click())
    }

    @Test
    fun loginFragment_login_wrongEmail_wrongCredentialsMessageShown() {
        launchFragment()

        //Given
        val wrongEmail = "a@a.a"

        //When
        setCredentials(wrongEmail, rightUserPassword)

        //Then

        // Check message is displayed
        onView(withId(R.id.login_wrong_credentials)).check(matches(isDisplayed()))

        // Check red color applied to hints
//        onView(withId(R.id.login_account_mail)).check { view, _ ->
//            if (!view.background.equals(android.R.color.holo_red_light)) fail()
//        }
//        onView(withId(R.id.login_account_password)).check { view, _ ->
//            if (!view.background.equals(android.R.color.holo_red_light)) fail()
//        }

    }

    @Test
    fun loginFragment_login_wrongPassword_wrongCredentialsMessageShown() {
        launchFragment()

        //Given
        val wrongPassword = "a"

        //When
        setCredentials(rightUserMail, wrongPassword)

        //Then

        // Check message is displayed
        onView(withId(R.id.login_wrong_credentials)).check(matches(isDisplayed()))

        // Check red color applied to hint
//        onView(withId(R.id.login_account_mail)).check { view, _ ->
//            if (!view.background.equals(android.R.color.holo_red_light)) fail()
//        }
    }

    @Test
    fun loginFragment_login_emptyEmail_wrongCredentialsMessageShown() {
        launchFragment()

        //Given
        val emptyEmail = ""

        //When
        setCredentials(emptyEmail, rightUserPassword)

        //Then

        // Check underline is displayed
        onView(withId(R.id.login_wrong_email)).check(matches(isDisplayed()))

        // Check red color applied to hint
//        onView(withId(R.id.login_account_mail)).check { view, _ ->
//            Log.d("Text Color:", (view as TextView).currentHintTextColor.toString())
//            if ((view as TextView).currentHintTextColor !=  android.R.color.holo_red_light) fail()
//        }
    }

    @Test
    fun loginFragment_login_emptyPassword_wrongCredentialsMessageShown() {
        launchFragment()

        //Given
        val emptyPassword = ""

        //When
        setCredentials(rightUserMail, emptyPassword)

        //Then

        // Check underline is displayed
        onView(withId(R.id.login_wrong_password)).check(matches(isDisplayed()))

        // Check red color applied to hint
//        onView(withId(R.id.login_account_password)).check { view, _ ->
//            if (!view.background.equals(android.R.color.holo_red_light)) fail()
//        }
    }

    @Test
    fun loginFragment_login_emptyEmailAndPassword_wrongCredentialsMessageShown() {
        launchFragment()

        //Given
        val emptyEmail = ""
        val emptyPassword = ""

        //When
        setCredentials(emptyEmail, emptyPassword)

        //Then

        // Check underline is displayed
        onView(withId(R.id.login_wrong_email)).check(matches(isDisplayed()))
        onView(withId(R.id.login_wrong_password)).check(matches(isDisplayed()))

        // Check red color applied to hint
//        onView(withId(R.id.login_account_mail)).check { view, _ ->
//            if (!view.background.equals(android.R.color.holo_red_light)) fail()
//        }
//        onView(withId(R.id.login_account_password)).check { view, _ ->
//            if (!view.background.equals(android.R.color.holo_red_light)) fail()
//        }
    }

    @Test
    fun loginFragment_login_rightCredentials_mainActivityLaunched() {
        launchFragment()

        //Given right credentials

        //When
        setCredentials(rightUserMail, rightUserPassword)

        //Then

        if (waitForViewWithId(R.id.userMainFragment, 5000)) {
            fail()
        }

    }

    @After
    fun finishActivity() {
        activityRule.finishActivity()
    }
}

