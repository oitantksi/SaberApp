package omega.isaacbenito.saberapp.authentication.login

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import omega.isaacbenito.saberapp.R
import omega.isaacbenito.saberapp.SaberApp
import omega.isaacbenito.saberapp.databinding.FragmentLoginBinding
import javax.inject.Inject

class LoginActivity() : AppCompatActivity() {

    val TAG = this.javaClass.name.toString()

    private lateinit var binding: FragmentLoginBinding

    @Inject lateinit var viewModel: LoginViewModel

    private lateinit var user: TextView
    private lateinit var password: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as SaberApp).appComponent.loginComponent().create().inject(this)
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.fragment_login)

        user = binding.accountMail
        password = binding.accountPassword

        binding.submit.setOnClickListener { login() }

        binding.newUser.setOnClickListener { viewModel.newUser }

        viewModel.newUser.observe(this, Observer { if(it) navigateToRegister() })
    }

    private fun navigateToRegister() {
        binding.root.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
    }

    private fun login() {
        viewModel.login(user.text.toString(), password.text.toString())
            .observe(this, Observer {  loginState ->
                when(loginState) {
//                    LoginSuccess -> navigate to user screen
//                    LoginError -> show error
                }
            })
    }
}

sealed class LoginState
object LoginSuccess : LoginState()
object LoginError : LoginState()
