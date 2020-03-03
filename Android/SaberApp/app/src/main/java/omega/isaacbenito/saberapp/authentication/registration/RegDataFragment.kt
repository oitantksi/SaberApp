package omega.isaacbenito.saberapp.authentication.registration

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

class RegDataFragment : Fragment() {

    val TAG = this.javaClass.name.toString()

    private lateinit var binding: FragmentRegisterBinding

    private lateinit var regDataViewModel: RegDataViewModel
    private lateinit var registrationViewModel: RegistrationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        regDataViewModel = ViewModelProviders.of(this).get(RegDataViewModel::class.java)
        registrationViewModel = ViewModelProviders.of(this).get(RegistrationViewModel::class.java)

        regDataViewModel.alreadyMember.observe(this, Observer { if(it) navigateToLogin() })

        regDataViewModel.enterDetailsState.observe(this, Observer { state ->
            when (state) {
                is EnterDataSuccess -> {
                    val user_name = binding.name.text.toString()
                    val user_surname = binding.surname.text.toString()
                    val user_nickname = ""
                    val email = binding.accountMail.text.toString()
                    val password = binding.accountPassword.text.toString()

                    registrationViewModel.updateUserData(
                        user_name, user_surname, user_nickname, email, password
                    )

                    (activity as RegistrationActivity).onDataEntered()
                }
                is EnterDataError -> {
                    //TODO Display user registration data error
                }
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)

        binding.alreadyMember.setOnClickListener { navigateToLogin() }

        binding.submit.setOnClickListener {
            val user_name = binding.name.text.toString()
            val user_surname = binding.surname.text.toString()
            val user_nickname = ""
            val email = binding.accountMail.text.toString()
            val password = binding.accountPassword.text.toString()

            regDataViewModel.validateInput(
                user_name, user_surname, user_nickname, email, password
            )
        }

        return binding.root
    }

    fun navigateToLogin() {
        binding.root.findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
    }

}

sealed class EnterDataState
object EnterDataSuccess : EnterDataState()
data class EnterDataError(val error: Int) : EnterDataState()