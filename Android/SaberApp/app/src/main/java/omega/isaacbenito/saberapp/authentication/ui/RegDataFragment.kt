/*
 * This file is part of SaberApp.
 *
 *     SaberApp is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     SaberApp is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with SaberApp.  If not, see <https://www.gnu.org/licenses/>.
 */

package omega.isaacbenito.saberapp.authentication.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import omega.isaacbenito.saberapp.R
import omega.isaacbenito.saberapp.authentication.model.RegDataViewModel
import omega.isaacbenito.saberapp.authentication.model.RegisterViewModel
import omega.isaacbenito.saberapp.authentication.ui.EnterDataError.Companion.INVALID_EMAIL
import omega.isaacbenito.saberapp.authentication.ui.EnterDataError.Companion.INVALID_NAME
import omega.isaacbenito.saberapp.authentication.ui.EnterDataError.Companion.INVALID_NICKNAME
import omega.isaacbenito.saberapp.authentication.ui.EnterDataError.Companion.INVALID_PASSWORD
import omega.isaacbenito.saberapp.authentication.ui.EnterDataError.Companion.INVALID_PASSWORD_REPEAT
import omega.isaacbenito.saberapp.authentication.ui.EnterDataError.Companion.INVALID_SURNAME
import omega.isaacbenito.saberapp.databinding.FragmentRegDataBinding
import javax.inject.Inject

/**
 * @author Isaac Benito
 *
 * Vista de recollida de dades per al registre de l'aplicació.
 *
 */
class RegDataFragment : Fragment() {

    private val _tag = this.javaClass.name

    private lateinit var binding: FragmentRegDataBinding

    @Inject
    lateinit var regDataViewModel: RegDataViewModel
    @Inject
    lateinit var registrationViewModel: RegisterViewModel

    /**
     * Es crida en crear el fragment.
     *
     * @param savedInstanceState
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /**
         * Crea un lligam amb el model de la vista i n' observa els canvis succeïts en la variable
         * de validació de les dades.
         *
         * En cas que les dades siguin vàlides comprova les desa en el model de l'activitat de
         * registre.
         *
         * En cas contrari mostra un missatge d'error explicant el motiu.
         */
        regDataViewModel.enterDetailsState.observe(this, Observer {
            when (it) {
                is EnterDataSuccess -> {
                    val userName = binding.registerName.text.toString()
                    val userSurname = binding.registerSurname.text.toString()
                    val userNickname = binding.registerNickname.text.toString()
                    val email = binding.registerAccountMail.text.toString()
                    val password = binding.registerAccountPassword.text.toString()

                    registrationViewModel.updateUserData(
                        userName, userSurname, userNickname, email, password
                    )

                    this.findNavController()
                        .navigate(R.id.action_regDataFragment_to_regCentreFragment)
                }
                is EnterDataError -> {
                    when (it.errorCode) {
                        INVALID_NAME -> {
                            val params =
                                binding.registerName.layoutParams as LinearLayout.LayoutParams
                            params.topMargin = 0
                            binding.registerName.setTextColor(
                                ContextCompat.getColor(context!!, android.R.color.holo_red_light)
                            )
                            binding.regWrongNameText.visibility = View.VISIBLE
                            binding.regWrongNameView.visibility = View.VISIBLE
                        }
                        INVALID_SURNAME -> {
                            val params =
                                binding.registerSurname.layoutParams as LinearLayout.LayoutParams
                            params.topMargin = 0
                            binding.registerSurname.setTextColor(
                                ContextCompat.getColor(context!!, android.R.color.holo_red_light)
                            )
                            binding.regWrongSurnameText.visibility = View.VISIBLE
                            binding.regWrongSurnameView.visibility = View.VISIBLE
                        }
                        INVALID_NICKNAME -> {
                            val params =
                                binding.registerNickname.layoutParams as LinearLayout.LayoutParams
                            params.topMargin = 0
                            binding.registerNickname.setTextColor(
                                ContextCompat.getColor(context!!, android.R.color.holo_red_light)
                            )
                            binding.regWrongNicknameText.visibility = View.VISIBLE
                            binding.regWrongNicknameView.visibility = View.VISIBLE
                        }
                        INVALID_EMAIL -> {
                            val params =
                                binding.registerAccountMail.layoutParams as LinearLayout.LayoutParams
                            params.topMargin = 0
                            binding.registerAccountMail.setTextColor(
                                ContextCompat.getColor(context!!, android.R.color.holo_red_light)
                            )
                            binding.regWrongMailText.visibility = View.VISIBLE
                            binding.regWrongMailView.visibility = View.VISIBLE
                        }
                        INVALID_PASSWORD -> {
                            val params =
                                binding.registerAccountPassword.layoutParams as LinearLayout.LayoutParams
                            params.topMargin = 0
                            binding.registerAccountPassword.setTextColor(
                                ContextCompat.getColor(context!!, android.R.color.holo_red_light)
                            )
                            binding.regWrongPasswordText.visibility = View.VISIBLE
                            binding.regWrongPasswordView.visibility = View.VISIBLE
                        }
                        INVALID_PASSWORD_REPEAT -> {
                            val params =
                                binding.registerAccountPasswordRepeat.layoutParams as LinearLayout.LayoutParams
                            params.topMargin = 0
                            binding.registerAccountPasswordRepeat.setTextColor(
                                ContextCompat.getColor(context!!, android.R.color.holo_red_light)
                            )
                            binding.regWrongPasswordRepeatText.visibility = View.VISIBLE
                            binding.regWrongPasswordRepeatView.visibility = View.VISIBLE
                        }

                    }
                }
            }
        })
    }

    /**
     * Es crida en crear la vista del fragment.
     * Estableix la vista de la pantalla i lliga el fragment amb les vistes necessàries
     * per a l'execucuó.
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_reg_data, container, false
        )

        // Al clickar en la opció de membre ja registrat es navega al fragment de login
        binding.alreadyMember.setOnClickListener {
            this.findNavController().navigate(R.id.action_regDataFragment_to_loginFragment)
        }


        binding.registerSubmitData.setOnClickListener { validateData() }

        return binding.root
    }

    /**
     * Es crida quan s'associa el fragment a l'activitat que el conté.
     *
     * @param context
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        /**
         * Usa l'instància del component d'autenticació de l'AuthActivity per a ingressar els
         * objectes de l'esquma de l'aplicació en els camps marcats amb l'anotació
         * @Inject
         */
        (activity as AuthActivity).authComponent.inject(this)
    }



    /**
     * Es crida quan l'usuari polsa el botó de registre.
     *
     * Envia les dades introduïdes per l'usuari al model de la vista per a que verifiqui
     * si compleixen les característiques necessàries per a crear un compte de
     * l'aplicació.
     */
    private fun validateData() {
        val userName = binding.registerName.text.toString()
        val userSurname = binding.registerSurname.text.toString()
        val userNickname = binding.registerNickname.text.toString()
        val email = binding.registerAccountMail.text.toString()
        val password = binding.registerAccountPassword.text.toString()
        val repeatPassword = binding.registerAccountPasswordRepeat.text.toString()

        regDataViewModel.validateInput(
            userName, userSurname, userNickname, email, password, repeatPassword
        )
    }

}



