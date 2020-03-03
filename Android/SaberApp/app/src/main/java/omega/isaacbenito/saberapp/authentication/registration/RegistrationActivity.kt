package omega.isaacbenito.saberapp.authentication.registration

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import omega.isaacbenito.saberapp.R
import omega.isaacbenito.saberapp.databinding.ActivityRegistrationBinding

class RegistrationActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRegistrationBinding

    lateinit var registrationViewModel: RegistrationViewModel

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        registrationViewModel = RegistrationViewModel()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_registration)

        navController = this.findNavController(R.id.registrationNavHostFragment)
    }

    fun onDataEntered() {
        navController.navigate(R.id.action_regDataFragment_to_regCentreFragment)
    }

    fun onCentreEntered() {
        registrationViewModel.registerUser()
        //TODO Navigate to user screen
        finish()
    }
}