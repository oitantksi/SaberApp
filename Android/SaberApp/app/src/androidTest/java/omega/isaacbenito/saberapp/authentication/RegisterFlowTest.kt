package omega.isaacbenito.saberapp.authentication

import androidx.navigation.NavDeepLinkBuilder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import omega.isaacbenito.saberapp.R
import omega.isaacbenito.saberapp.authentication.ui.AuthActivity
import omega.isaacbenito.saberapp.ui.MainActivity
import omega.isaacbenito.saberapp.user.ui.CentreAdapter
import omega.isaacbenito.saberapp.utils.TestUtils.Companion.waitFor
import omega.isaacbenito.saberapp.utils.TestUtils.Companion.waitForRecyclerViewToFill
import org.junit.After
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@LargeTest
class RegisterFlowTest {

    @Inject
    lateinit var authManager: AuthenticationManager

    @get:Rule
    private val activityRule = IntentsTestRule(AuthActivity::class.java)

    private val rightName = "Name"
    private val rightSurname = "First Second"
    private val rightNickname = "yabadabadoo"
    private val rightUserMail = "email@email.com"
    private val rightPassword = "Just@F8P4ssword!"
    private val centre = 1
    private val role = 'A'

    companion object {
        @BeforeClass
        fun setup() {
            Intents.init()
        }
    }

    @After
    fun unregister() {
        authManager.unregister(rightUserMail)
    }


    private fun launchFragment() {
        activityRule.launchActivity(
            NavDeepLinkBuilder(
                InstrumentationRegistry.getInstrumentation().targetContext
            )
                .setGraph(R.navigation.auth_navigation)
                .setComponentName(AuthActivity::class.java)
                .setDestination(R.id.regDataFragment)
                .createTaskStackBuilder().intents[0]
        )
    }

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
            rightSurname,
            rightNickname,
            rightUserMail,
            rightPassword,
            rightPassword
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
            rightName,
            emptySurname,
            rightNickname,
            rightUserMail,
            rightPassword,
            rightPassword
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
            rightName,
            rightSurname,
            emptyNickname,
            rightUserMail,
            rightPassword,
            rightPassword
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
            rightName,
            rightSurname,
            rightNickname,
            emptyMail,
            rightPassword,
            rightPassword
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
            rightName,
            rightSurname,
            rightNickname,
            rightUserMail,
            emptyPassword,
            rightPassword
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
            rightName,
            rightSurname,
            rightNickname,
            rightUserMail,
            rightPassword,
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
            rightSurname,
            rightNickname,
            rightUserMail,
            rightPassword,
            rightPassword
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
            rightName,
            wrongSurname,
            rightNickname,
            rightUserMail,
            rightPassword,
            rightPassword
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
            rightName,
            rightSurname,
            wrongNickname,
            rightUserMail,
            rightPassword,
            rightPassword
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
            rightName,
            rightSurname,
            rightNickname,
            wrongMail,
            rightPassword,
            rightPassword
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
            rightName,
            rightSurname,
            rightNickname,
            rightUserMail,
            wrongPassword,
            rightPassword
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
            rightName,
            rightSurname,
            rightNickname,
            rightUserMail,
            rightPassword,
            wrongPasswordRepeat
        )
        onView(withId(R.id.register_submitData)).perform(click())

        //Then
        onView(withId(R.id.reg_wrong_passwordRepeat_view)).check(matches(isDisplayed()))

        onView(withId(R.id.reg_wrong_passwordRepeat_text)).check(matches(isDisplayed()))
    }

    @Test
    fun registerFlow_rightCredentials_mainActivityLaunched() {
        launchFragment()

        //Given input is all right
        setRegDataInput(
            rightName,
            rightSurname,
            rightNickname,
            rightUserMail,
            rightPassword,
            rightPassword
        )

        //When register button clicked
        onView(withId(R.id.register_submitData)).perform(click())

        //Then select school fragment is shown
        waitFor(
            {onView(withId(R.id.centre_list)).check(matches(isDisplayed()))},
            2000
        )

        //Given schools are shown
        waitForRecyclerViewToFill(activityRule.activity, R.id.centre_list, 5000)

        //When a school is chosen
        onView(withId(R.id.centre_list)).perform(
            RecyclerViewActions.actionOnItemAtPosition<CentreAdapter.CentreVH>(
                centre,
                click()
            )
        )

        //Then MainActivity is launched
        waitFor({intended(hasComponent(MainActivity::class.java.name))}, 5000)

    }

    @After
    fun finishActivity() {
        activityRule.finishActivity()
    }
}