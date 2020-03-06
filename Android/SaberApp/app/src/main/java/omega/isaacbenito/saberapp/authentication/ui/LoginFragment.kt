package omega.isaacbenito.saberapp.authentication.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import omega.isaacbenito.saberapp.NetworkUtils
import omega.isaacbenito.saberapp.R
import omega.isaacbenito.saberapp.authentication.model.LoginViewModel
import omega.isaacbenito.saberapp.authentication.model.RegisterViewModel
import omega.isaacbenito.saberapp.databinding.FragmentLoginBinding
import javax.inject.Inject

class LoginFragment() : Fragment() {

    val TAG = this.javaClass.name

    private lateinit var binding: FragmentLoginBinding

    @Inject lateinit var loginViewModel: LoginViewModel

    @Inject lateinit var networkUtils: NetworkUtils

    private lateinit var user: TextView
    private lateinit var password: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loginViewModel.loginState.observe(viewLifecycleOwner, Observer { loginState ->
            when (loginState) {
                is LoginSuccess -> Toast.makeText(context, "Login succesful",
                    Toast.LENGTH_LONG).show()
                //TODO On login succes navigate to user fragment

                is ServerUnreachable -> Toast.makeText(context, R.string.server_unreachable,
                    Toast.LENGTH_LONG).show()
                is WrongCredentials -> Toast.makeText(context, R.string.wrong_credentials,
                    Toast.LENGTH_LONG).show()

            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)

        user = binding.accountMail
        password = binding.accountPassword

        binding.submit.setOnClickListener { login() }

        binding.newUser.setOnClickListener {
            this.findNavController().navigate(R.id.action_loginFragment_to_regDataFragment)
        }

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as AuthActivity).authComponent.inject(this)
    }

    private fun navigateToRegister() {
        binding.root.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
    }

    private fun login() {
        if (networkUtils.isConnected) {
            loginViewModel.login(user.text.toString(), password.text.toString())
        } else {
            Toast.makeText(context, R.string.no_network, Toast.LENGTH_LONG).show()
        }
    }
}

sealed class LoginState
object LoginSuccess : LoginState()
sealed class LoginError : LoginState()
object ServerUnreachable : LoginError()
object WrongCredentials : LoginError()

