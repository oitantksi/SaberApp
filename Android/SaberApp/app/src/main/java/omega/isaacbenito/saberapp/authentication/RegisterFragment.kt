package omega.isaacbenito.saberapp.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import omega.isaacbenito.saberapp.R
import omega.isaacbenito.saberapp.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    val TAG = this.javaClass.name.toString()

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(RegisterViewModel::class.java)

        viewModel.alreadyMember.observe(this, Observer { if(it) navigateToLogin() })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)

        binding.alreadyMember.setOnClickListener { navigateToLogin() }

        return binding.root
    }

    fun navigateToLogin() {
        binding.root.findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
    }

}