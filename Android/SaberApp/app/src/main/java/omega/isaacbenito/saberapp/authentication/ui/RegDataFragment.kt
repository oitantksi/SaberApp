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
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import omega.isaacbenito.saberapp.R
import omega.isaacbenito.saberapp.authentication.model.RegisterViewModel
import omega.isaacbenito.saberapp.authentication.model.RegDataViewModel
import omega.isaacbenito.saberapp.authentication.ui.EnterDataError.Companion.INVALID_EMAIL
import omega.isaacbenito.saberapp.authentication.ui.EnterDataError.Companion.INVALID_NAME
import omega.isaacbenito.saberapp.authentication.ui.EnterDataError.Companion.INVALID_NICKNAME
import omega.isaacbenito.saberapp.authentication.ui.EnterDataError.Companion.INVALID_PASSWORD
import omega.isaacbenito.saberapp.authentication.ui.EnterDataError.Companion.INVALID_PASSWORD_REPEAT
import omega.isaacbenito.saberapp.authentication.ui.EnterDataError.Companion.INVALID_SURNAME
import omega.isaacbenito.saberapp.databinding.FragmentRegDataBinding
import omega.isaacbenito.saberapp.utils.NetworkUtils
import javax.inject.Inject

/**
 * @author Isaac Benito
 *
 * Vista de recollida de dades per al registre de l'aplicació.
 *
 */
class RegDataFragment : Fragment() {

    val TAG = this.javaClass.name

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
                    val user_name = binding.name.text.toString()
                    val user_surname = binding.surname.text.toString()
                    val user_nickname = binding.nickname.text.toString()
                    val email = binding.accountMail.text.toString()
                    val password = binding.accountPassword.text.toString()

                    registrationViewModel.updateUserData(
                        user_name, user_surname, user_nickname, email, password
                    )

                    this.findNavController()
                        .navigate(R.id.action_regDataFragment_to_regCentreFragment)
                }
                is EnterDataError -> {
                    when (it.errorCode) {
                        INVALID_EMAIL ->
                            Toast.makeText(
                                context,
                                R.string.invalid_email,
                                Toast.LENGTH_SHORT
                            ).show()
                        INVALID_PASSWORD ->
                            Toast.makeText(
                                context,
                                R.string.invalid_password,
                                Toast.LENGTH_SHORT
                            ).show()
                        INVALID_PASSWORD_REPEAT ->
                            Toast.makeText(
                                context,
                                R.string.invalid_password_repeat,
                                Toast.LENGTH_SHORT
                            ).show()
                        INVALID_NAME ->
                            Toast.makeText(
                                context,
                                R.string.invalid_name,
                                Toast.LENGTH_SHORT
                            ).show()
                        INVALID_SURNAME ->
                            Toast.makeText(
                                context,
                                R.string.invalid_name,
                                Toast.LENGTH_SHORT
                            ).show()
                        INVALID_NICKNAME ->
                            Toast.makeText(
                                context,
                                R.string.invalid_nickname,
                                Toast.LENGTH_SHORT
                            ).show()
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


        binding.submitData.setOnClickListener { validateData() }

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
    fun validateData() {
        val user_name = binding.name.text.toString()
        val user_surname = binding.surname.text.toString()
        val user_nickname = binding.nickname.text.toString()
        val email = binding.accountMail.text.toString()
        val password = binding.accountPassword.text.toString()
        val repeatPassword = binding.accountPasswordRepeat.text.toString()

        regDataViewModel.validateInput(
            user_name, user_surname, user_nickname, email, password, repeatPassword
        )
    }

}



