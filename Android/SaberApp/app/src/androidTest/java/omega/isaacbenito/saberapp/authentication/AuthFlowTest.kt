package omega.isaacbenito.saberapp.authentication

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import omega.isaacbenito.saberapp.R
import omega.isaacbenito.saberapp.authentication.ui.LoginFragment
import omega.isaacbenito.saberapp.authentication.ui.RegDataFragment
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class AuthFlowTest {

    val mockNavController = mock(NavController::class.java)

    @Test
    fun loginFragment_newUserClicked_regDataFragmentShown() {
        // Given
        val fragmentScenario = launchFragmentInContainer<LoginFragment>()
        fragmentScenario.onFragment {
            Navigation.setViewNavController(it.requireView(), mockNavController)
        }

        //When
        onView(withId(R.id.newUser)).perform(click())

        //Then
        verify(mockNavController).navigate(R.id.action_loginFragment_to_regDataFragment)
    }

    @Test
    fun regDataFragment_alreadyUserClicked_loginFragmentShown() {
        //Given
        val fragmentScenario = launchFragmentInContainer<RegDataFragment>()
        fragmentScenario.onFragment {
            Navigation.setViewNavController(it.requireView(), mockNavController)
        }

        //When
        onView(withId(R.id.alreadyMember)).perform(click())

        //Then
        verify(mockNavController).navigate(R.id.action_regDataFragment_to_loginFragment)
    }

    @Test
    fun regDataFragment_backPressed_loginFragmentShown() {}

    @Test
    fun regCentreFragment_backPressed_regDataFragmentShown() {}
}