package omega.isaacbenito.saberapp

import org.junit.Test

class RegisterFlowTest {


    @Test
    fun register_user() {
        val name = "Name"
        val surname = "First Second"
        val nickname = "yabadabadoo"
        val userMail = "email@email.com"
        val password = "Just@F8P4ssword!"
        val centre = 1
        val role = 'A'

//        launchFragment(R.id.regDataFragment)
//
//        onView(ViewMatchers.withId(R.id.name)).perform(
//            ViewActions.typeText(name),
//            ViewActions.closeSoftKeyboard()
//        )
//
//        onView(ViewMatchers.withId(R.id.surname)).perform(
//            ViewActions.typeText(surname),
//            ViewActions.closeSoftKeyboard()
//        )
//
//        onView(ViewMatchers.withId(R.id.nickname)).perform(
//            ViewActions.typeText(nickname),
//            ViewActions.closeSoftKeyboard()
//        )
//
//        onView(ViewMatchers.withId(R.id.login_account_mail)).perform(
//            ViewActions.typeText(userMail),
//            ViewActions.closeSoftKeyboard()
//        )
//
//        onView(withId(R.id.`login_account_ password`)).perform(
//            ViewActions.typeText(password),
//            ViewActions.closeSoftKeyboard()
//        )
//
//        onView(ViewMatchers.withId(R.id.accountPassword_repeat)).perform(
//            ViewActions.typeText(password),
//            ViewActions.closeSoftKeyboard()
//        )
//
//        onView(ViewMatchers.withId(R.id.register_submitData)).perform(ViewActions.click())
//
//        if (!waitForViewWithText("IOC", 5000)) {
//            TestCase.fail()
//        }
//
//        onView(ViewMatchers.withId(R.id.centre_list)).perform(
//            RecyclerViewActions.actionOnItemAtPosition<CentreAdapter.CentreVH>(
//                centre,
//                ViewActions.click()
//            )
//        )
//
//        if (!waitForViewWithId(R.id.userMainFragment_layout, 5000)) {
//            TestCase.fail()
//        }
    }
}