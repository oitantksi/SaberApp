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
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val regDataViewModel by viewModels<RegDataViewModel> { viewModelFactory }

    private val registrationViewModel by viewModels<RegisterViewModel> { viewModelFactory }

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

                    binding.registerName.text.clear()
                    binding.registerSurname.text.clear()
                    binding.registerNickname.text.clear()
                    binding.registerAccountMail.text.clear()
                    binding.registerAccountPassword.text.clear()

                    when (it.errorCode) {
                        INVALID_NAME -> {
                            binding.registerName.setHintTextColor(
                                ContextCompat.getColor(context!!, android.R.color.holo_red_light)
                            )
                            binding.regWrongNameText.visibility = View.VISIBLE
                            binding.regWrongNameView.visibility = View.VISIBLE
                        }
                        INVALID_SURNAME -> {
                            binding.registerSurname.setHintTextColor(
                                ContextCompat.getColor(context!!, android.R.color.holo_red_light)
                            )
                            binding.regWrongSurnameText.visibility = View.VISIBLE
                            binding.regWrongSurnameView.visibility = View.VISIBLE
                        }
                        INVALID_NICKNAME -> {
                            binding.registerNickname.setHintTextColor(
                                ContextCompat.getColor(context!!, android.R.color.holo_red_light)
                            )
                            binding.regWrongNicknameText.visibility = View.VISIBLE
                            binding.regWrongNicknameView.visibility = View.VISIBLE
                        }
                        INVALID_EMAIL -> {
                            binding.regWrongMailText.visibility = View.VISIBLE
                            binding.regWrongMailView.visibility = View.VISIBLE
                            binding.registerAccountMail.setHintTextColor(
                                ContextCompat.getColor(context!!, android.R.color.holo_red_light)
                            )
                        }
                        INVALID_PASSWORD -> {
                            binding.registerAccountPassword.setHintTextColor(
                                ContextCompat.getColor(context!!, android.R.color.holo_red_light)
                            )
                            binding.regWrongPasswordText.visibility = View.VISIBLE
                            binding.regWrongPasswordView.visibility = View.VISIBLE
                        }
                        INVALID_PASSWORD_REPEAT -> {
                            binding.registerAccountPasswordRepeat.setHintTextColor(
                                ContextCompat.getColor(context!!, android.R.color.holo_red_light)
                            )
                            binding.regWrongPasswordRepeatText.visibility = View.VISIBLE
                            binding.regWrongPasswordRepeatView.visibility = View.VISIBLE
                        }

                    }
                }
            }
        })

        regDataViewModel.alreadyMember.observe(this, Observer {
            this.findNavController().navigate(R.id.action_regDataFragment_to_loginFragment)
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

        binding.regDataVM = regDataViewModel

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





}



