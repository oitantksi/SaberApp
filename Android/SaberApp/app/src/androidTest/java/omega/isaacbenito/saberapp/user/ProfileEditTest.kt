package omega.isaacbenito.saberapp.user

import android.content.Intent
import androidx.navigation.NavDeepLinkBuilder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import omega.isaacbenito.saberapp.R
import omega.isaacbenito.saberapp.ui.MainActivity
import omega.isaacbenito.saberapp.utils.TestUtils.Companion.waitFor
import org.hamcrest.core.StringContains.containsString
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Tests per a probar la funcionalitat d'edició de dades en el [UserProfileFragment]
 *
 * @author Isaac Benito
 **/
class ProfileEditTest {

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun initUserComponent() {
        val mainActivity = activityRule.launchActivity(Intent(InstrumentationRegistry.getInstrumentation().targetContext,
            MainActivity::class.java)
        )

    }

    @After
    fun finishActivity() {
        activityRule.finishActivity()
    }


    private fun launchFragment() {
        activityRule.launchActivity(
            NavDeepLinkBuilder(
                InstrumentationRegistry.getInstrumentation().targetContext
            )
                .setGraph(R.navigation.main_navigation)
                .setComponentName(MainActivity::class.java)
                .setDestination(R.id.userProfileFragment)
                .createTaskStackBuilder().intents[0]
        )


    }

    private val rightName = "Name"
    private val rightSurname = "First Second"
    private val rightNickname = "yabadabadoo"
    private val rightUserMail = "email@email.com"
    private val rightPassword = "Just@F8P4ssword!"
    private val centre = 1

    private fun onEditField_rigthEdit_fieldChanged(editLayout: Int, editField: Int, editText: String) {
        //Given
        launchFragment()

        // When click to edit
        onView(withId(editLayout)).perform(click())

        // Then Edit dialog is shown
        waitFor(
            { onView(withId(R.id.edit_profile_layout)).check(matches(isDisplayed()))},
            2000
        )

        // When perform edit
        onView(withId(R.id.first_editProfile_editText)).perform(typeText(editText))

        closeSoftKeyboard()

        onView(withId(R.id.edit_profile_done)).perform(click())

        // Then assert field changed
        waitFor(
            {onView(withId(editField)).check(matches(withText(containsString(editText))))},
            2000
        )
    }

    @Test
    fun nameEdit_rightName_nameChanged() {
        onEditField_rigthEdit_fieldChanged(R.id.userName_layout, R.id.userName, rightName)
    }

    @Test
    fun nameEdit_rightSurname_surnameChanged() {
        onEditField_rigthEdit_fieldChanged(R.id.userSurname_layout,R.id.userSurname, rightSurname)
    }

    @Test
    fun nameEdit_rightNickname_nicknameChanged() {
        onEditField_rigthEdit_fieldChanged(R.id.userNickname_layout, R.id.userNickname, rightNickname)
    }

    @Test
    fun nameEdit_rightEmail_emailChanged() {
        onEditField_rigthEdit_fieldChanged(R.id.email_layout, R.id.email, rightUserMail)
    }


    fun onEditField_emptyEdit_SnacbarShown(editField: Int, snackbarMessage: Int) {
        //Given
        launchFragment()

        //When
        onView(withId(editField)).perform(click())

        //Then
        waitFor(
            { onView(withId(R.id.edit_profile_layout)).check(matches(isDisplayed()))},
            2000
        )

        //When
        onView(withId(R.id.first_editProfile_editText)).perform(typeText(""))

        onView(withId(R.id.edit_profile_done)).perform(click())

        //Then
        waitFor(
            {onView(withId(com.google.android.material.R.id.snackbar_text)).let {
                it.check(matches(isDisplayed()))
                it.check(matches(withText(snackbarMessage)))}},
            2000
        )
    }
//
//    @Test
//    fun nameEdit_emptyName_SnackBarShown() {
//        onEditField_emptyEdit_SnacbarShown(R.id.userName_layout, R.string.invalid_name)
//    }
//
//    @Test
//    fun nameEdit_emptySurname_SnackBarShown() {
//        onEditField_emptyEdit_SnacbarShown(R.id.userSurname_layout, R.string.invalid_name)
//    }
//
//    @Test
//    fun nameEdit_emptyNickame_SnackBarShown() {
//        onEditField_emptyEdit_SnacbarShown(R.id.userNickname_layout, R.string.invalid_nickname)
//    }
//
//    @Test
//    fun nameEdit_emptyEmail_SnackBarShown() {
//        onEditField_emptyEdit_SnacbarShown(R.id.email_layout, R.string.invalid_email)
//    }



}