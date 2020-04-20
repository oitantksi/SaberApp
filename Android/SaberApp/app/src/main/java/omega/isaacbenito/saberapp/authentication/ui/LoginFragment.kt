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

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import dagger.android.support.DaggerFragment
import omega.isaacbenito.saberapp.R
import omega.isaacbenito.saberapp.authentication.AuthResult
import omega.isaacbenito.saberapp.authentication.EnterDataResult
import omega.isaacbenito.saberapp.authentication.model.LoginViewModel
import omega.isaacbenito.saberapp.databinding.FragmentLoginBinding
import omega.isaacbenito.saberapp.ui.MainActivity
import omega.isaacbenito.saberapp.utils.NetworkUtils
import timber.log.Timber
import javax.inject.Inject

/**
 * @author Isaac Benito
 *
 * Vista de login de l'aplicació
 */
class LoginFragment : DaggerFragment() {

    private lateinit var binding: FragmentLoginBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val loginViewModel by viewModels<LoginViewModel> { viewModelFactory }

    @Inject
    lateinit var networkUtils: NetworkUtils

    private lateinit var user: TextView
    private lateinit var password: TextView

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
         * En cas que les dades siguin vàlides comprova l'estat de connexió a la xarxa i envia la
         * petició de login al gestor d'autenticació.
         *
         * En cas de que no hi hagi accés a la xarxa mostra un missatge d'error.
         *
         * En cas que les dades no siguin vàlides mostra un missatge d'error però no especifica
         * el motiu per el qual no són vàlides per a no proveir d'eines per a intentar
         * falsejar unes credencials.
         */
        loginViewModel.enterDataResult.observe(this, Observer {
            when (it) {
                is EnterDataResult.Error -> {
                    binding.loginAccountMail.text.clear()
                    binding.loginAccountPassword.text.clear()

                    binding.loginWrongCredentials.visibility = View.VISIBLE

                    binding.loginAccountMail.setHintTextColor(
                        ContextCompat.getColor(context!!, android.R.color.holo_red_light)
                    )
                    binding.loginWrongEmail.visibility = View.VISIBLE
                    binding.loginAccountPassword.setHintTextColor(
                        ContextCompat.getColor(context!!, android.R.color.holo_red_light)
                    )
                    binding.loginWrongPassword.visibility = View.VISIBLE
                }

                is EnterDataResult.Success ->
                    if (networkUtils.isNetworkConnected()) {
                        Timber.d("Call login")
                        loginViewModel.login()
                    } else {
                        Toast.makeText(context, R.string.no_network, Toast.LENGTH_LONG).show()
                    }
            }
        })

        /**
         * Crea un lligam am el model de la vista i n' observa els canvis succeïts en la variable
         * d'estat de l'autenticació.
         *
         * En cas que l'autenticació sigui exitosa llança l'activitat principal de l'aplicació
         *
         * En cas contrari mostra un missatge especificant l'error
         */
        loginViewModel.loginResult.observe(this, Observer { result: AuthResult ->
            when (result) {
                is AuthResult.Success ->
                    startActivity(Intent(context, MainActivity::class.java))
                is AuthResult.Error ->
                    when (result.error) {
                        AuthResult.Error.WRONG_CREDENTIALS_ERROR ->
                            binding.loginWrongCredentials.visibility = View.VISIBLE
                        AuthResult.Error.SERVER_UNREACHABLE_ERROR ->
                            Toast.makeText(
                                context, R.string.server_unreachable,
                                Toast.LENGTH_SHORT
                            ).show()
                    }
            }
        })

        loginViewModel.newUser.observe(this, Observer {
            this.findNavController().navigate(R.id.action_loginFragment_to_regDataFragment)
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        binding.viewModel = loginViewModel

        user = binding.loginAccountMail

        password = binding.loginAccountPassword

        return binding.root
    }



    /**
     * Es crida quan l'usuari polsa el botó de login.
     *
     * Envia les dades introduïdes per l'usuari al model de la vista per a que verifiqui
     * si compleixen les característiques necessàries per a ser unes credencials
     * d'accés a l'aplicació.
     */
//    private fun login() {
//        loginViewModel.validateInput(user.text.toString(), password.text.toString())
//    }
}



