package omega.isaacbenito.saberapp

import android.os.Bundle
import androidx.fragment.app.testing.launchFragment
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavDeepLinkBuilder
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Rule
import org.junit.runner.RunWith
import androidx.test.rule.ActivityTestRule
import omega.isaacbenito.saberapp.authentication.model.LoginViewModel
import omega.isaacbenito.saberapp.authentication.ui.AuthActivity
import omega.isaacbenito.saberapp.authentication.ui.LoginFragment
import org.junit.After
import org.junit.Before
import org.junit.Test

@LargeTest
class AuthModuleTest {

    private lateinit var userMail: String
    private lateinit var password: String

//    @get:Rule
//    var activityRule: ActivityTestRule<AuthActivity> =
//        ActivityTestRule(AuthActivity::class.java)

    @get:Rule
    var activityRule = ActivityTestRule<AuthActivity>(AuthActivity::class.java)

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
        launchFragment(R.id.loginFragment)
    }

    @Test
    fun loginFragment_login_rightCredentials() {
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


    }

    @After
    fun finishLoginFragment() {
        activityRule.finishActivity()
    }



//
//    fun createVM() {
//        var viewModel = LoginViewModel()
//    }
//
//    fun setUserCredentials() {

//    }
//
//    @Test
//    fun login() {
//        // Recrea el fragment
//        val scenario = launchFragment<LoginFragment>()
//

//    }
}