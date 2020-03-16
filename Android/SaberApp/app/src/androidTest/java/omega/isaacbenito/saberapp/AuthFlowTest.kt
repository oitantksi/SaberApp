package omega.isaacbenito.saberapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import omega.isaacbenito.saberapp.authentication.ui.AuthActivity
import omega.isaacbenito.saberapp.utils.TestUtils.Companion.getFragmentIntent
import omega.isaacbenito.saberapp.utils.TestUtils.Companion.waitForViewWithId
import org.junit.Rule
import org.junit.Test

class AuthFlowTest {

    @get:Rule
    var activityRule = ActivityTestRule(AuthActivity::class.java)

    @Test
    fun loginFragment_newUserClicked_regDataFragmentShown() {
        //Given
        activityRule.launchActivity(getFragmentIntent(R.id.loginFragment))

        //When
        onView(withId(R.id.newUser)).perform(click())

        //Then
        waitForViewWithId(R.id.regDataFragment, 2000)
    }

    @Test
    fun regDataFragment_alreadyUserClicked_loginFragmentShown() {
        //Given
        activityRule.launchActivity(getFragmentIntent(R.id.regDataFragment))

        //When
        onView(withId(R.id.alreadyMember)).perform(click())

        //Then
        waitForViewWithId(R.id.loginFragment, 2000)
    }
}