package omega.isaacbenito.saberapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.runner.RunWith
import androidx.test.rule.ActivityTestRule
import omega.isaacbenito.saberapp.authentication.login.LoginActivity
import org.junit.Before
import org.junit.Test

@RunWith(AndroidJUnit4::class)
@LargeTest
class AuthModuleTest {

    private lateinit var userMail: String
    private lateinit var password: String

    @get:Rule
    var activityRule: ActivityTestRule<LoginActivity> =
        ActivityTestRule(LoginActivity::class.java)

    @Before
    fun setUserCredentials() {
        userMail = "isaac.benito@omega.com"
        password = "omega"
    }

    @Test
    fun setText() {
        //Type mail
        onView(withId(R.id.accountMail)).perform(
            typeText(userMail)
        )
        onView(withId(R.id.accountPassword)).perform(
            typeText(password),
            closeSoftKeyboard()
        )
    }

}