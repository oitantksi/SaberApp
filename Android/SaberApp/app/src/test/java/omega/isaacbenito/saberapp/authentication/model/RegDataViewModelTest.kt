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
import omega.isaacbenito.saberapp.authentication.EnterDataResult
import omega.isaacbenito.saberapp.authentication.EnterDataResult.Error.Companion.INVALID_NAME
import omega.isaacbenito.saberapp.authentication.EnterDataResult.Error.Companion.INVALID_SURNAME
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class RegDataViewModelTest {

    private val regDataViewModel = RegDataViewModel()

    private val rightUserName = "Name"
    private val rightUserSurname = "Surname Surname"
    private val rightUserNickname = "Nickname"
    private val rightEmail = "email@email.com"
    private val rightPassword = "Aa@45678"

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    private fun setData(
        userName: String,
        userSurname: String,
        userNickname: String,
        userMail: String,
        userPassword: String,
        userPasswordRepeat: String
    ) {
        regDataViewModel.userName.set(userName)
        regDataViewModel.userSurname.set(userSurname)
        regDataViewModel.userNickname.set(userNickname)
        regDataViewModel.userMail.set(userMail)
        regDataViewModel.userPassword.set(userPassword)
        regDataViewModel.userPasswordRepeat.set(userPasswordRepeat)
    }

    @Test
    fun validateInput_rightData_EnterDataSuccess() {
        // Given right data

        // When
        setData(
            rightUserName,
            rightUserSurname,
            rightUserNickname,
            rightEmail,
            rightPassword,
            rightPassword
        )

        regDataViewModel.validateInput()

        // Then
        val expected = EnterDataResult.Success

        assertEquals(expected, regDataViewModel.enterDetailsResult.value)
    }


    private fun validateName(name: String) {
        setData(
            name,
            rightUserSurname,
            rightUserNickname,
            rightEmail,
            rightPassword,
            rightPassword
        )

        regDataViewModel.validateInput()
    }

    private val expectedNameError = EnterDataResult.Error(INVALID_NAME)

    @Test
    fun validateInput_nameTooShort_EnterDataError() {
        //Given
        val name = "a"

        //When
        validateName(name)

        //Then
        assertEquals(expectedNameError, regDataViewModel.enterDetailsResult.value)
    }

    @Test
    fun validateInput_nameTooLong_EnterDataError() {
        //Given
        val name = "a".repeat(52)

        //When
        validateName(name)

        //Then
        assertEquals(expectedNameError, regDataViewModel.enterDetailsResult.value)
    }

    @Test
    fun validateInput_nameWithSymbols_EnterDataError() {
        //Given
        val name = "aaaa?"

        //When
        validateName(name)

        //Then
        assertEquals(expectedNameError, regDataViewModel.enterDetailsResult.value)
    }

    @Test
    fun validateInput_nameWithNumbers_EnterDataError() {
        //Given
        val name = "aaaa4"

        //When
        validateName(name)

        //Then
        assertEquals(expectedNameError, regDataViewModel.enterDetailsResult.value)
    }


    private fun validateSurname(surname: String) {
        setData(
            rightUserName,
            surname,
            rightUserNickname,
            rightEmail,
            rightPassword,
            rightPassword
        )

        regDataViewModel.validateInput()
    }

    private val expectedSurnameError = EnterDataResult.Error(INVALID_SURNAME)

    @Test
    fun validateInput_surnameTooShort_EnterDataError() {
        //When
        val surname = "a"

        //When
        validateSurname(surname)

        //Then
        assertEquals(expectedSurnameError, regDataViewModel.enterDetailsResult.value)
    }

    @Test
    fun validateInput_surnameTooLong_EnterDataError() {
        //When
        val surname = "a".repeat(51)

        //When
        validateSurname(surname)

        //Then
        assertEquals(expectedSurnameError, regDataViewModel.enterDetailsResult.value)
    }

    @Test
    fun validateInput_surnameWithDigits_EnterDataError() {
        //When
        val surname = "aaaa?"

        //When
        validateSurname(surname)

        //Then
        assertEquals(expectedSurnameError, regDataViewModel.enterDetailsResult.value)
    }

    @Test
    fun validateInput_surnameWithSymbols_EnterDataError() {
        //When
        val surname = "aaaa4"

        //When
        validateSurname(surname)

        //Then
        assertEquals(expectedSurnameError, regDataViewModel.enterDetailsResult.value)
    }


    private fun validateNickname(nickname: String) {
        setData(
            rightUserName,
            rightUserSurname,
            nickname,
            rightEmail,
            rightPassword,
            rightPassword
        )

        regDataViewModel.validateInput()
    }

    private val expectedNicknameError = EnterDataResult.Error(EnterDataResult.Error.INVALID_NICKNAME)

    @Test
    fun validateInput_nicknameTooShort_EnterDataError() {
        //Given
        val nickname = "a".repeat(16)

        //When
        validateNickname(nickname)

        //Then
        assertEquals(expectedNicknameError, regDataViewModel.enterDetailsResult.value)
    }

    @Test
    fun validateInput_nicknameTooLong_EnterDataError() {
        //Given
        val nickname = "a".repeat(16)

        //When
        validateNickname(nickname)

        //Then
        assertEquals(expectedNicknameError, regDataViewModel.enterDetailsResult.value)
    }

    @Test
    fun validateInput_nicknameWithUnallowedSymbols_EnterDataError() {
        // Given
        val nickname = "aaaa?"

        //When
        validateNickname(nickname)

        //Then
        assertEquals(expectedNicknameError, regDataViewModel.enterDetailsResult.value)
    }


    private fun validateEmail(email: String) {
        setData(
            rightUserName,
            rightUserSurname,
            rightUserNickname,
            email,
            rightPassword,
            rightPassword
        )

        regDataViewModel.validateInput()
    }

    private val expectedEmailError = EnterDataResult.Error(EnterDataResult.Error.INVALID_EMAIL)

    @Test
    fun validateInput_email_EnterDataError() {
        //Given
        val email = ""

        //When
        validateEmail(email)

        //Then
        assertEquals(expectedEmailError, regDataViewModel.enterDetailsResult.value)
    }

    @Test
    fun validateInput_emailWithoutLocal_EnterDataError() {
        //Given
        val email = "@a.a"

        //When
        validateEmail(email)

        //Then
        assertEquals(expectedEmailError, regDataViewModel.enterDetailsResult.value)
    }

    @Test
    fun validateInput_emailWithoutHostname_EnterDataError() {
        //Given
        val email = "a@.a"

        //When
        validateEmail(email)

        //Then
        assertEquals(expectedEmailError, regDataViewModel.enterDetailsResult.value)
    }

    @Test
    fun validateInput_emailWithoutHostnameDomain_EnterDataError() {
        //Given
        val email = "a@a"

        //When
        validateEmail(email)

        //Then
        assertEquals(expectedEmailError, regDataViewModel.enterDetailsResult.value)
    }

    @Test
    fun validateInput_emailWithoutAt_EnterDataError() {
        //Given
        val email = "aa.com"

        //When
        validateEmail(email)

        //Then
        assertEquals(expectedEmailError, regDataViewModel.enterDetailsResult.value)
    }

    @Test
    fun validateInput_emailLocalTooLong_EnterDataError() {
        //Given
        val email = "${"a".repeat(257)}@a.a"

        //When
        validateEmail(email)

        //Then
        assertEquals(expectedEmailError, regDataViewModel.enterDetailsResult.value)
    }

    @Test
    fun validateInput_emailHostnameTooLong_EnterDataError() {
        //Given
        val email = "a@${"a".repeat(65)}.a"

        //When
        validateEmail(email)

        //Then
        assertEquals(expectedEmailError, regDataViewModel.enterDetailsResult.value)
    }

    @Test
    fun validateInput_emailHostnameDomainTooLong_EnterDataError() {
        //Given
        val email = "a@a.${"a".repeat(26)}"

        //When
        validateEmail(email)

        //Then
        assertEquals(expectedEmailError, regDataViewModel.enterDetailsResult.value)
    }

    @Test
    fun validateInput_emailWithMultipleAt_EnterDataError() {
        //Given
        val email = "a@@a.a"

        //When
        validateEmail(email)

        //Then
        assertEquals(expectedEmailError, regDataViewModel.enterDetailsResult.value)
    }

    @Test
    fun validateInput_emailHostnameWithNotAllowedSymbols_EnterDataError() {
        //Given
        val email = "a@a&a.a"

        //When
        validateEmail(email)

        //Then
        assertEquals(expectedEmailError, regDataViewModel.enterDetailsResult.value)
    }

    @Test
    fun validateInput_emailHostnameDomainWithNotAllowedSymbols_EnterDataError() {
        //Given
        val email = "a@a.a&a"

        //When
        validateEmail(email)

        //Then
        assertEquals(expectedEmailError, regDataViewModel.enterDetailsResult.value)
    }


    private fun validatePassword(password: String) {
        setData(
            rightUserName,
            rightUserSurname,
            rightUserNickname,
            rightEmail,
            password,
            rightPassword
        )

        regDataViewModel.validateInput()
    }

    private val expectedPasswordError = EnterDataResult.Error(EnterDataResult.Error.INVALID_PASSWORD)

    @Test
    fun validateInput_passwordTooShort_EnterDataError() {
        //Given
        val password = "Aa@4"

        //When
        validatePassword(password)

        //Then
        assertEquals(expectedPasswordError, regDataViewModel.enterDetailsResult.value)
    }

    @Test
    fun validateInput_passwordWithouthLower_EnterDataError() {
        //Given
        val password = "A@345678"

        //When
        validatePassword(password)

        //Then
        assertEquals(expectedPasswordError, regDataViewModel.enterDetailsResult.value)
    }

    @Test
    fun validateInput_passwordWithoutUpper_EnterDataError() {
        //Given
        val password = "a@345678"

        //When
        validatePassword(password)

        //Then
        assertEquals(expectedPasswordError, regDataViewModel.enterDetailsResult.value)
    }

    @Test
    fun validateInput_passwordWithoutDigit_EnterDataError() {
        //Given
        val password = "Aa@aaaaa"

        //When
        validatePassword(password)

        //Then
        assertEquals(expectedPasswordError, regDataViewModel.enterDetailsResult.value)
    }

    @Test
    fun validateInput_passwordWithoutSymbol_EnterDataError() {
        //Given
        val password = "Aa345678"

        //When
        validatePassword(password)

        //Then
        assertEquals(expectedPasswordError, regDataViewModel.enterDetailsResult.value)
    }

    @Test
    fun validateInput_passwordWithNotAllowedSymbol_EnterDataError() {
        //Given
        val password = "Aa@4/678"

        //When
        validatePassword(password)

        //Then
        assertEquals(expectedPasswordError, regDataViewModel.enterDetailsResult.value)
    }

    @Test
    fun validateInput_wrongPasswordConfirmation_EnterDataError() {
        val password1 = "Aa@45678"
        val password2 = "Aa@45679"

        // When
        setData(
            rightUserName,
            rightUserSurname,
            rightUserNickname,
            rightEmail,
            password1,
            password2
        )

        regDataViewModel.validateInput()

        // Then
        val expected = EnterDataResult.Error(EnterDataResult.Error.INVALID_PASSWORD_REPEAT)

        assertEquals(expected, regDataViewModel.enterDetailsResult.value)
    }
}