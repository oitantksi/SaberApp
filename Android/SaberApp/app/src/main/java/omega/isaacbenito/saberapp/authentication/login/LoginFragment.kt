package omega.isaacbenito.saberapp.authentication.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import omega.isaacbenito.saberapp.R
import omega.isaacbenito.saberapp.databinding.FragmentLoginBinding

class LoginFragment() : Fragment() {

    val TAG = this.javaClass.name.toString()

    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel

    private lateinit var user: TextView
    private lateinit var password: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        viewModel.newUser.observe(this, Observer { if(it) navigateToRegister() })
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

        binding.newUser.setOnClickListener { navigateToRegister() }

        return binding.root
    }

    private fun navigateToRegister() {
        binding.root.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
    }

    private fun login() {
        viewModel.login(user.text.toString(), password.text.toString())
    }
}
