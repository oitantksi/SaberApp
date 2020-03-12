/*
 * This file is part of SaberApp.
 *
 *     SaberApp is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     SaberApp is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with SaberApp.  If not, see <https://www.gnu.org/licenses/>.
 */

package omega.isaacbenito.saberapp.authentication.model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import omega.isaacbenito.saberapp.authentication.ui.EnterDataError
import omega.isaacbenito.saberapp.authentication.ui.EnterDataSuccess
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class RegDataViewModelTest {

    private val regDataViewModel = RegDataViewModel()

    private var right_user_name = "Name"
    private var right_user_surname = "Surname Surname"
    private var right_user_nickname = "Nickname"
    private var right_email = "email@email.com"
    private var right_password = "Aa@45678"

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }


    @Test
    fun validateInput_rightData_EnterDataSuccess() {
        // Given right data

        // When
        regDataViewModel.validateInput(
            right_user_name,
            right_user_surname,
            right_user_nickname,
            right_email,
            right_password,
            right_password
        )

        // Then
        val expected = EnterDataSuccess

        assertEquals(expected, regDataViewModel.enterDetailsState.value)
    }


    fun validateName(name: String) {
        regDataViewModel.validateInput(
            name,
            right_user_surname,
            right_user_nickname,
            right_email,
            right_password,
            right_password
        )
    }

    val expected_nameError = EnterDataError(EnterDataError.INVALID_NAME)

    @Test
    fun validateInput_nameTooShort_EnterDataError() {
        //Given
        val name = "a"

        //When
        validateName(name)

        //Then
        assertEquals(expected_nameError, regDataViewModel.enterDetailsState.value)
    }

    @Test
    fun validateInput_nameTooLong_EnterDataError() {
        //Given
        val name = "a".repeat(52)

        //When
        validateName(name)

        //Then
        assertEquals(expected_nameError, regDataViewModel.enterDetailsState.value)
    }

    @Test
    fun validateInput_nameWithSymbols_EnterDataError() {
        //Given
        val name = "aaaa?"

        //When
        validateName(name)

        //Then
        assertEquals(expected_nameError, regDataViewModel.enterDetailsState.value)
    }

    @Test
    fun validateInput_nameWithNumbers_EnterDataError() {
        //Given
        val name = "aaaa4"

        //When
        validateName(name)

        //Then
        assertEquals(expected_nameError, regDataViewModel.enterDetailsState.value)
    }


    fun validateSurname(surname: String) {
        regDataViewModel.validateInput(
            right_user_name,
            surname,
            right_user_nickname,
            right_email,
            right_password,
            right_password
        )
    }

    val expected_surnameError = EnterDataError(EnterDataError.INVALID_SURNAME)

    @Test
    fun validateInput_surnameTooShort_EnterDataError() {
        //When
        val surname = "a"

        //When
        validateSurname(surname)

        //Then
        assertEquals(expected_surnameError, regDataViewModel.enterDetailsState.value)
    }

    @Test
    fun validateInput_surnameTooLong_EnterDataError() {
        //When
        val surname = "a".repeat(51)

        //When
        validateSurname(surname)

        //Then
        assertEquals(expected_surnameError, regDataViewModel.enterDetailsState.value)
    }

    @Test
    fun validateInput_surnameWithDigits_EnterDataError() {
        //When
        val surname = "aaaa?"

        //When
        validateSurname(surname)

        //Then
        assertEquals(expected_surnameError, regDataViewModel.enterDetailsState.value)
    }

    @Test
    fun validateInput_surnameWithSymbols_EnterDataError() {
        //When
        val surname = "aaaa4"

        //When
        validateSurname(surname)

        //Then
        assertEquals(expected_surnameError, regDataViewModel.enterDetailsState.value)
    }


    fun validateNickname(nickname: String) {
        regDataViewModel.validateInput(
            right_user_name,
            right_user_surname,
            nickname,
            right_email,
            right_password,
            right_password
        )
    }

    val expected_nicknameError = EnterDataError(EnterDataError.INVALID_NICKNAME)

    @Test
    fun validateInput_nicknameTooShort_EnterDataError() {
        //Given
        val nickname = "a".repeat(16)

        //When
        validateNickname(nickname)

        //Then
        assertEquals(expected_nicknameError, regDataViewModel.enterDetailsState.value)
    }

    @Test
    fun validateInput_nicknameTooLong_EnterDataError() {
        //Given
        val nickname = "a".repeat(16)

        //When
        validateNickname(nickname)

        //Then
        assertEquals(expected_nicknameError, regDataViewModel.enterDetailsState.value)
    }

    @Test
    fun validateInput_nicknameWithUnallowedSymbols_EnterDataError() {
        // Given
        val nickname = "aaaa?"

        //When
        validateNickname(nickname)

        //Then
        assertEquals(expected_nicknameError, regDataViewModel.enterDetailsState.value)
    }


    fun validateEmail(email: String) {
        regDataViewModel.validateInput(
            right_user_name,
            right_user_surname,
            right_user_nickname,
            email,
            right_password,
            right_password
        )
    }

    val expected_emailError = EnterDataError(EnterDataError.INVALID_EMAIL)

    @Test
    fun validateInput_email_EnterDataError() {
        //Given
        val email = ""

        //When
        validateEmail(email)

        //Then
        assertEquals(expected_emailError, regDataViewModel.enterDetailsState.value)
    }

    @Test
    fun validateInput_emailWithoutLocal_EnterDataError() {
        //Given
        val email = "@a.a"

        //When
        validateEmail(email)

        //Then
        assertEquals(expected_emailError, regDataViewModel.enterDetailsState.value)
    }

    @Test
    fun validateInput_emailWithoutHostname_EnterDataError() {
        //Given
        val email = "a@.a"

        //When
        validateEmail(email)

        //Then
        assertEquals(expected_emailError, regDataViewModel.enterDetailsState.value)
    }

    @Test
    fun validateInput_emailWithoutHostnameDomain_EnterDataError() {
        //Given
        val email = "a@a"

        //When
        validateEmail(email)

        //Then
        assertEquals(expected_emailError, regDataViewModel.enterDetailsState.value)
    }

    @Test
    fun validateInput_emailWithoutAt_EnterDataError() {
        //Given
        val email = "aa.com"

        //When
        validateEmail(email)

        //Then
        assertEquals(expected_emailError, regDataViewModel.enterDetailsState.value)
    }

    @Test
    fun validateInput_emailLocalTooLong_EnterDataError() {
        //Given
        val email = "${"a".repeat(257)}@a.a"

        //When
        validateEmail(email)

        //Then
        assertEquals(expected_emailError, regDataViewModel.enterDetailsState.value)
    }

    @Test
    fun validateInput_emailHostnameTooLong_EnterDataError() {
        //Given
        val email = "a@${"a".repeat(65)}.a"

        //When
        validateEmail(email)

        //Then
        assertEquals(expected_emailError, regDataViewModel.enterDetailsState.value)
    }

    @Test
    fun validateInput_emailHostnameDomainTooLong_EnterDataError() {
        //Given
        val email = "a@a.${"a".repeat(26)}"

        //When
        validateEmail(email)

        //Then
        assertEquals(expected_emailError, regDataViewModel.enterDetailsState.value)
    }

    @Test
    fun validateInput_emailWithMultipleAt_EnterDataError() {
        //Given
        val email = "a@@a.a"

        //When
        validateEmail(email)

        //Then
        assertEquals(expected_emailError, regDataViewModel.enterDetailsState.value)
    }

    @Test
    fun validateInput_emailHostnameWithNotAllowedSymbols_EnterDataError() {
        //Given
        val email = "a@a&a.a"

        //When
        validateEmail(email)

        //Then
        assertEquals(expected_emailError, regDataViewModel.enterDetailsState.value)
    }

    @Test
    fun validateInput_emailHostnameDomainWithNotAllowedSymbols_EnterDataError() {
        //Given
        val email = "a@a.a&a"

        //When
        validateEmail(email)

        //Then
        assertEquals(expected_emailError, regDataViewModel.enterDetailsState.value)
    }


    fun validatePassword(password: String) {
        regDataViewModel.validateInput(
            right_user_name,
            right_user_surname,
            right_user_nickname,
            right_email,
            password,
            right_password
        )
    }

    val expected_passwordError = EnterDataError(EnterDataError.INVALID_PASSWORD)

    @Test
    fun validateInput_passwordTooShort_EnterDataError() {
        //Given
        val password = "Aa@4"

        //When
        validatePassword(password)

        //Then
        assertEquals(expected_passwordError, regDataViewModel.enterDetailsState.value)
    }

    @Test
    fun validateInput_passwordWithouthLower_EnterDataError() {
        //Given
        val password = "A@345678"

        //When
        validatePassword(password)

        //Then
        assertEquals(expected_passwordError, regDataViewModel.enterDetailsState.value)
    }

    @Test
    fun validateInput_passwordWithoutUpper_EnterDataError() {
        //Given
        val password = "a@345678"

        //When
        validatePassword(password)

        //Then
        assertEquals(expected_passwordError, regDataViewModel.enterDetailsState.value)
    }

    @Test
    fun validateInput_passwordWithoutDigit_EnterDataError() {
        //Given
        val password = "Aa@aaaaa"

        //When
        validatePassword(password)

        //Then
        assertEquals(expected_passwordError, regDataViewModel.enterDetailsState.value)
    }

    @Test
    fun validateInput_passwordWithoutSymbol_EnterDataError() {
        //Given
        val password = "Aa345678"

        //When
        validatePassword(password)

        //Then
        assertEquals(expected_passwordError, regDataViewModel.enterDetailsState.value)
    }

    @Test
    fun validateInput_passwordWithNotAllowedSymbol_EnterDataError() {
        //Given
        val password = "Aa@4/678"

        //When
        validatePassword(password)

        //Then
        assertEquals(expected_passwordError, regDataViewModel.enterDetailsState.value)
    }

    @Test
    fun validateInput_wrongPasswordConfirmation_EnterDataError() {
        val password1 = "Aa@45678"
        val password2 = "Aa@45679"

        // When
        regDataViewModel.validateInput(
            right_user_name,
            right_user_surname,
            right_user_nickname,
            right_email,
            password1,
            password2
        )

        // Then
        val expected = EnterDataError(EnterDataError.INVALID_PASSWORD_REPEAT)

        assertEquals(expected, regDataViewModel.enterDetailsState.value)
    }
}