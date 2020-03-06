package omega.isaacbenito.saberapp.authentication.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import omega.isaacbenito.saberapp.R
import omega.isaacbenito.saberapp.SaberApp
import omega.isaacbenito.saberapp.authentication.model.RegisterViewModel
import omega.isaacbenito.saberapp.databinding.ActivityAuthBinding
import omega.isaacbenito.saberapp.di.AuthComponent
import javax.inject.Inject

class AuthActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAuthBinding

    @Inject lateinit var authViewModel: RegisterViewModel

    lateinit var authComponent: AuthComponent

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {

        authComponent = (application as SaberApp).appComponent
            .authComponent().create()
        authComponent.inject(this)

        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_auth)

        navController = this.findNavController(R.id.authNavHostFragment)
    }

    fun isNewUser() {
        navController.navigate(R.id.action_loginFragment_to_regDataFragment)
    }

    fun alreadyMember() {
        navController.navigate(R.id.action_regDataFragment_to_loginFragment)
    }

    fun onDataEntered() {
        navController.navigate(R.id.action_regDataFragment_to_regCentreFragment)
    }

    fun onCentreEntered() {
        authViewModel.registerUser()
        //TODO Navigate to user screen
        finish()
    }
}

sealed class RegistrationState
object RegistrationSuccesful : RegistrationState()
data class RegistrationError (val error: String) : RegistrationState()


