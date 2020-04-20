package omega.isaacbenito.saberapp.authentication

import androidx.navigation.NavDeepLinkBuilder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import omega.isaacbenito.saberapp.R
import omega.isaacbenito.saberapp.authentication.ui.AuthActivity
import omega.isaacbenito.saberapp.utils.TestUtils.Companion.waitFor
import org.junit.After
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

class LoginFragmentTest {

    private val rightUserMail = "isaac@omega.com"
    private val rightUserPassword = "Abr@kd4bra!"

    @Inject
    lateinit var authManager: AuthenticationManager

    @get:Rule
    private val activityRule = ActivityTestRule(AuthActivity::class.java)

    private fun launchFragment() {
        activityRule.launchActivity(
            NavDeepLinkBuilder(
                InstrumentationRegistry.getInstrumentation().targetContext
            )
                .setGraph(R.navigation.auth_navigation)
                .setComponentName(AuthActivity::class.java)
                .setDestination(R.id.loginFragment)
                .createTaskStackBuilder().intents[0]
        )
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
        waitFor(
            {onView(withId(R.id.userMainFragment)).check(matches(isDisplayed()))},
            5000
        )
    }

    @After
    fun finishActivity() {
        activityRule.finishActivity()
    }

    @After
    fun logout() {
        authManager.logout(rightUserMail)
    }
}

