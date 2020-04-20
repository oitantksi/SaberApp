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

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import dagger.android.support.DaggerFragment
import omega.isaacbenito.saberapp.R
import omega.isaacbenito.saberapp.authentication.EnterDataResult
import omega.isaacbenito.saberapp.authentication.EnterDataResult.Error.Companion.INVALID_EMAIL
import omega.isaacbenito.saberapp.authentication.EnterDataResult.Error.Companion.INVALID_NAME
import omega.isaacbenito.saberapp.authentication.EnterDataResult.Error.Companion.INVALID_NICKNAME
import omega.isaacbenito.saberapp.authentication.EnterDataResult.Error.Companion.INVALID_PASSWORD
import omega.isaacbenito.saberapp.authentication.EnterDataResult.Error.Companion.INVALID_PASSWORD_REPEAT
import omega.isaacbenito.saberapp.authentication.EnterDataResult.Error.Companion.INVALID_SURNAME
import omega.isaacbenito.saberapp.authentication.model.RegDataViewModel
import omega.isaacbenito.saberapp.authentication.model.RegisterViewModel
import omega.isaacbenito.saberapp.databinding.FragmentRegDataBinding
import javax.inject.Inject

/**
 * @author Isaac Benito
 *
 * Vista de recollida de dades per al registre de l'aplicació.
 *
 */
class RegDataFragment : DaggerFragment() {

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
        regDataViewModel.enterDetailsResult.observe(this, Observer {
            when (it) {
                is EnterDataResult.Success -> {
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
                is EnterDataResult.Error -> {

                    when (it.errorCode) {
                        INVALID_NAME -> {
                            binding.registerName.text.clear()
                            binding.registerName.setHintTextColor(
                                ContextCompat.getColor(context!!, android.R.color.holo_red_light)
                            )
                            binding.regWrongNameText.visibility = View.VISIBLE
                            binding.regWrongNameView.visibility = View.VISIBLE
                        }
                        INVALID_SURNAME -> {
                            binding.registerSurname.text.clear()
                            binding.registerSurname.setHintTextColor(
                                ContextCompat.getColor(context!!, android.R.color.holo_red_light)
                            )
                            binding.regWrongSurnameText.visibility = View.VISIBLE
                            binding.regWrongSurnameView.visibility = View.VISIBLE
                        }
                        INVALID_NICKNAME -> {
                            binding.registerNickname.text.clear()
                            binding.registerNickname.setHintTextColor(
                                ContextCompat.getColor(context!!, android.R.color.holo_red_light)
                            )
                            binding.regWrongNicknameText.visibility = View.VISIBLE
                            binding.regWrongNicknameView.visibility = View.VISIBLE
                        }
                        INVALID_EMAIL -> {
                            binding.registerAccountMail.text.clear()
                            binding.regWrongMailText.visibility = View.VISIBLE
                            binding.regWrongMailView.visibility = View.VISIBLE
                            binding.registerAccountMail.setHintTextColor(
                                ContextCompat.getColor(context!!, android.R.color.holo_red_light)
                            )
                        }
                        INVALID_PASSWORD -> {
                            binding.registerAccountPassword.text.clear()
                            binding.registerAccountPasswordRepeat.text.clear()
                            binding.registerAccountPassword.setHintTextColor(
                                ContextCompat.getColor(context!!, android.R.color.holo_red_light)
                            )
                            binding.regWrongPasswordText.visibility = View.VISIBLE
                            binding.regWrongPasswordView.visibility = View.VISIBLE
                        }
                        INVALID_PASSWORD_REPEAT -> {
                            binding.registerAccountPassword.text.clear()
                            binding.registerAccountPasswordRepeat.text.clear()
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
}



