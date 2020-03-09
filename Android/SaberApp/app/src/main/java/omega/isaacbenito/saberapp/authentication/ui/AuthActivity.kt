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
}

sealed class AuthState()
object AuthSuccess : AuthState()
data class AuthError(val error: Int) : AuthState() {
    companion object {
        val SERVER_UNREACHABLE_ERROR = 0
        val WRONG_CREDENTIALS_ERROR = 1
    }
}


