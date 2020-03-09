package omega.isaacbenito.saberapp.authentication.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import omega.isaacbenito.saberapp.R
import omega.isaacbenito.saberapp.authentication.model.RegisterViewModel
import omega.isaacbenito.saberapp.authentication.model.RegDataViewModel
import omega.isaacbenito.saberapp.databinding.FragmentRegDataBinding
import javax.inject.Inject

class RegDataFragment : Fragment() {

    val TAG = this.javaClass.name.toString()

    private lateinit var binding: FragmentRegDataBinding

    @Inject lateinit var regDataViewModel: RegDataViewModel
    @Inject lateinit var registrationViewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

                    this.findNavController().navigate(R.id.action_regDataFragment_to_regCentreFragment)
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_reg_data, container, false)

        binding.alreadyMember.setOnClickListener {
            this.findNavController().navigate(R.id.action_regDataFragment_to_loginFragment)
        }


        binding.submitData.setOnClickListener {
            val user_name = binding.name.text.toString()
            val user_surname = binding.surname.text.toString()
            val user_nickname = binding.nickname.text.toString()
            val email = binding.accountMail.text.toString()
            val password = binding.accountPassword.text.toString()

            regDataViewModel.validateInput(
                user_name, user_surname, user_nickname, email, password
            )
        }

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (activity as AuthActivity).authComponent.inject(this)
    }

}

sealed class EnterDataState
object EnterDataSuccess : EnterDataState()
sealed class EnterDataError : EnterDataState()
object InvalidEmail : EnterDataError()
object InvalidPassword : EnterDataError()

