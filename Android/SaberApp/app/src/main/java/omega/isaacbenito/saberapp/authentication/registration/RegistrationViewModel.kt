package omega.isaacbenito.saberapp.authentication.registration

import androidx.lifecycle.ViewModel

class RegistrationViewModel : ViewModel() {

    private var user_name: String? = null
    private var user_surname: String? = null
    private var user_nickname: String? = null
    private var email: String? = null
    private var password: String? = null
    private var centre: String? = null
    private var curs: String? = null
    private var aula: String? = null

    fun updateUserData(
        user_name: String,
        user_surname: String,
        user_nickname: String,
        email: String,
        password: String
    ) {
        this.user_name = user_name
        this.user_surname = user_surname
        this.user_nickname = user_nickname
        this.email = email
        this.password = password
    }

    fun updateCentreData(
        centre: String,
        curs: String,
        aula: String
    ) {
        this.centre = centre
        this.curs = curs
        this.aula = aula
    }

    fun registerUser() {
        assert(user_name != null)
        assert(user_surname != null)
        assert(user_nickname != null)
        assert(email != null)
        assert(password != null)
        assert(centre != null)
        assert(curs != null)
        assert(aula != null)

        //TODO Call userManager for user registration
    }


}