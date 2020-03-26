package omega.isaacbenito.saberapp.authentication

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import junit.framework.TestCase
import omega.isaacbenito.saberapp.R
import omega.isaacbenito.saberapp.authentication.ui.AuthActivity
import omega.isaacbenito.saberapp.authentication.ui.CentreAdapter
import omega.isaacbenito.saberapp.ui.MainActivity
import omega.isaacbenito.saberapp.utils.TestUtils
import omega.isaacbenito.saberapp.utils.TestUtils.Companion.waitForViewWithText
import org.junit.After
import org.junit.Rule
import org.junit.Test

@LargeTest
class RegisterFlowTest {

    @get:Rule
    private val activityRule = ActivityTestRule(AuthActivity::class.java)

    private val testedFragment = R.id.regDataFragment

    private fun launchFragment() {
        activityRule.launchActivity(TestUtils.getFragmentIntent(testedFragment))
    }

    private val rigthName = "Name"
    private val rigthSurname = "First Second"
    private val rigthNickname = "yabadabadoo"
    private val rigthUserMail = "email@email.com"
    private val rigthPassword = "Just@F8P4ssword!"
    private val centre = 1
    private val role = 'A'

    private fun setRegDataInput(
        userName: String,
        userSurname: String,
        userNicname: String,
        userMail: String,
        userPassword: String,
        userPasswordRepeat: String
    ) {

        onView(withId(R.id.register_name)).perform(
            ViewActions.typeText(userName),
            ViewActions.closeSoftKeyboard()
        )

        onView(withId(R.id.register_surname)).perform(
            ViewActions.typeText(userSurname),
            ViewActions.closeSoftKeyboard()
        )

        onView(withId(R.id.register_nickname)).perform(
            ViewActions.typeText(userNicname),
            ViewActions.closeSoftKeyboard()
        )

        onView(withId(R.id.register_account_mail)).perform(
            ViewActions.typeText(userMail),
            ViewActions.closeSoftKeyboard()
        )

        onView(withId(R.id.register_account_password)).perform(
            ViewActions.typeText(userPassword),
            ViewActions.closeSoftKeyboard()
        )

        onView(withId(R.id.register_accountPassword_repeat)).perform(
            ViewActions.typeText(userPasswordRepeat),
            ViewActions.closeSoftKeyboard()
        )
    }

    @Test
    fun registerDataFragment_emptyName_redUnderlineTextViewShown() {
        launchFragment()

        //Given
        val emptyName = ""

        //When
        setRegDataInput(
            emptyName,
            rigthSurname,
            rigthNickname,
            rigthUserMail,
            rigthPassword,
            rigthPassword
        )
        onView(withId(R.id.register_submitData)).perform(click())

        //Then
        onView(withId(R.id.reg_wrong_name_view)).check(matches(isDisplayed()))
    }

    @Test
    fun registerDataFragment_emptySurname_redUnderlineTextViewShown() {
        launchFragment()

        //Given
        val emptySurname = ""

        //When
        setRegDataInput(
            rigthName,
            emptySurname,
            rigthNickname,
            rigthUserMail,
            rigthPassword,
            rigthPassword
        )
        onView(withId(R.id.register_submitData)).perform(click())

        //Then
        onView(withId(R.id.reg_wrong_surname_view)).check(matches(isDisplayed()))
    }

    @Test
    fun registerDataFragment_emptyNickname_redUnderlineTextViewShown() {
        launchFragment()

        //Given
        val emptyNickname = ""

        //When
        setRegDataInput(
            rigthName,
            rigthSurname,
            emptyNickname,
            rigthUserMail,
            rigthPassword,
            rigthPassword
        )
        onView(withId(R.id.register_submitData)).perform(click())

        //Then
        onView(withId(R.id.reg_wrong_nickname_view)).check(matches(isDisplayed()))
    }

    @Test
    fun registerDataFragment_emptyMail_redUnderlineTextViewShown() {
        launchFragment()

        //Given
        val emptyMail = ""

        //When
        setRegDataInput(
            rigthName,
            rigthSurname,
            rigthNickname,
            emptyMail,
            rigthPassword,
            rigthPassword
        )
        onView(withId(R.id.register_submitData)).perform(click())

        //Then
        onView(withId(R.id.reg_wrong_mail_view)).check(matches(isDisplayed()))
    }

    @Test
    fun registerDataFragment_emptyPassword_redUnderlineTextViewShown() {
        launchFragment()

        //Given
        val emptyPassword = ""

        //When
        setRegDataInput(
            rigthName,
            rigthSurname,
            rigthNickname,
            rigthUserMail,
            emptyPassword,
            rigthPassword
        )
        onView(withId(R.id.register_submitData)).perform(click())

        //Then
        onView(withId(R.id.reg_wrong_password_view)).check(matches(isDisplayed()))
    }

    @Test
    fun registerDataFragment_emptyPasswordRepeat_redUnderlineTextViewShown() {
        launchFragment()

        //Given
        val emptyPasswordRepeat = ""

        //When
        setRegDataInput(
            rigthName,
            rigthSurname,
            rigthNickname,
            rigthUserMail,
            rigthPassword,
            emptyPasswordRepeat
        )
        onView(withId(R.id.register_submitData)).perform(click())

        //Then
        onView(withId(R.id.reg_wrong_passwordRepeat_view)).check(matches(isDisplayed()))
    }

    @Test
    fun registerDataFragment_wrongName_alertMessageShown() {
        launchFragment()

        //Given
        val wrongName = ""

        //When
        setRegDataInput(
            wrongName,
            rigthSurname,
            rigthNickname,
            rigthUserMail,
            rigthPassword,
            rigthPassword
        )
        onView(withId(R.id.register_submitData)).perform(click())

        //Then
        onView(withId(R.id.reg_wrong_name_view)).check(matches(isDisplayed()))

        onView(withId(R.id.reg_wrong_name_text)).check(matches(isDisplayed()))
    }

    @Test
    fun registerDataFragment_wrongSurname_alertMessageShown() {
        launchFragment()

        //Given
        val wrongSurname = ""

        //When
        setRegDataInput(
            rigthName,
            wrongSurname,
            rigthNickname,
            rigthUserMail,
            rigthPassword,
            rigthPassword
        )
        onView(withId(R.id.register_submitData)).perform(click())

        //Then
        onView(withId(R.id.reg_wrong_surname_view)).check(matches(isDisplayed()))

        onView(withId(R.id.reg_wrong_surname_text)).check(matches(isDisplayed()))
    }

    @Test
    fun registerDataFragment_wrongNickname_alertMessageShown() {
        launchFragment()

        //Given
        val wrongNickname = ""

        //When
        setRegDataInput(
            rigthName,
            rigthSurname,
            wrongNickname,
            rigthUserMail,
            rigthPassword,
            rigthPassword
        )
        onView(withId(R.id.register_submitData)).perform(click())

        //Then
        onView(withId(R.id.reg_wrong_nickname_view)).check(matches(isDisplayed()))

        onView(withId(R.id.reg_wrong_nickname_text)).check(matches(isDisplayed()))
    }

    @Test
    fun registerDataFragment_wrongMail_alertMessageShown() {
        launchFragment()

        //Given
        val wrongMail = ""

        //When
        setRegDataInput(
            rigthName,
            rigthSurname,
            rigthNickname,
            wrongMail,
            rigthPassword,
            rigthPassword
        )
        onView(withId(R.id.register_submitData)).perform(click())

        //Then
        onView(withId(R.id.reg_wrong_mail_view)).check(matches(isDisplayed()))

        onView(withId(R.id.reg_wrong_mail_text)).check(matches(isDisplayed()))
    }

    @Test
    fun registerDataFragment_wrongPassword_alertMessageShown() {
        launchFragment()

        //Given
        val wrongPassword = ""

        //When
        setRegDataInput(
            rigthName,
            rigthSurname,
            rigthNickname,
            rigthUserMail,
            wrongPassword,
            rigthPassword
        )
        onView(withId(R.id.register_submitData)).perform(click())

        //Then
        onView(withId(R.id.reg_wrong_password_view)).check(matches(isDisplayed()))

        onView(withId(R.id.reg_wrong_password_text)).check(matches(isDisplayed()))
    }

    @Test
    fun registerDataFragment_wrongPasswordRepeat_alertMessageShown() {
        launchFragment()

        //Given
        val wrongPasswordRepeat = ""

        //When
        setRegDataInput(
            rigthName,
            rigthSurname,
            rigthNickname,
            rigthUserMail,
            rigthPassword,
            wrongPasswordRepeat
        )
        onView(withId(R.id.register_submitData)).perform(click())

        //Then
        onView(withId(R.id.reg_wrong_passwordRepeat_view)).check(matches(isDisplayed()))

        onView(withId(R.id.reg_wrong_passwordRepeat_text)).check(matches(isDisplayed()))
    }

    @Test
    fun registerFlow_rigthCredentials_mainActivityLaunched() {
        launchFragment()

        //Given input is all right
        setRegDataInput(
            rigthName,
            rigthSurname,
            rigthNickname,
            rigthUserMail,
            rigthPassword,
            rigthPassword
        )

        //When register button clicked
        onView(withId(R.id.register_submitData)).perform(click())

        //Then select school fragment is shown
        if (!waitForViewWithText("IOC", 5000)) {
            TestCase.fail()
        }

        //When a school is chosen
        onView(withId(R.id.centre_list)).perform(
            RecyclerViewActions.actionOnItemAtPosition<CentreAdapter.CentreVH>(
                centre,
                click()
            )
        )

        //Then MainActivity is launched
        intended(hasComponent(MainActivity::javaClass.name))
    }

    @After
    fun finishActivity() {
        activityRule.finishActivity()
    }
}