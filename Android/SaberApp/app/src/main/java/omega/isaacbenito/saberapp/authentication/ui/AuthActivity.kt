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
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import omega.isaacbenito.saberapp.R
import omega.isaacbenito.saberapp.SaberApp
import omega.isaacbenito.saberapp.authentication.model.RegisterViewModel
import omega.isaacbenito.saberapp.databinding.ActivityAuthBinding
import omega.isaacbenito.saberapp.di.AuthComponent
import javax.inject.Inject

/**
 * @author Isaac Benito
 *
 * Activitat que gestiona el flux de registre i/o login de l'usuari.
 *
 */
class AuthActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAuthBinding

    @Inject lateinit var authViewModel: RegisterViewModel

    lateinit var authComponent: AuthComponent

    private lateinit var navController: NavController

    /**
     * Es crida quan es crea l'activitat.
     *
     * @param savedInstanceState
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        /**
         * Usa l'esquema de l'aplicació per a ingressar els
         * objectes de l'esquma de l'aplicació en els camps marcats amb l'anotació
         * @Inject.
         */
        authComponent = (application as SaberApp).appComponent
            .authComponent().create()
        authComponent.inject(this)

        super.onCreate(savedInstanceState)

        /**
         * Estableix la vista de l'aplicació.
         */
        binding = DataBindingUtil.setContentView(this, R.layout.activity_auth)

        /**
         * Llança el fragment de login.
         */
        navController = this.findNavController(R.id.authNavHostFragment)
    }

    /**
     * Override back navigation to disallow it.
     */
    override fun onBackPressed() {}
}

/**
 * @author Isaac Benito
 *
 * Classe sellada que facilita la comunicació de l'estat del procés d'autorització
 * entre les diferents activitats i models del mòdul
 *
 */
sealed class AuthState
object AuthSuccess : AuthState()
data class AuthError(val error: Int) : AuthState() {
    companion object {
        const val SERVER_UNREACHABLE_ERROR = 0
        const val NO_INTERNET_ACCESS = 1
        const val WRONG_CREDENTIALS_ERROR = 2
    }
}

/**
 * @author Isaac Benito
 *
 * Classe sellada que facilita la comunicació de la validesa de les dades introduïdes
 * entre les diferents activitats i models del mòdul
 *
 */
sealed class EnterDataState
object EnterDataSuccess : EnterDataState()
data class EnterDataError(val errorCode: Int) : EnterDataState() {
    companion object {
        const val INVALID_EMAIL = 0
        const val INVALID_PASSWORD = 2
        const val INVALID_PASSWORD_REPEAT = 3
        const val INVALID_NAME = 4
        const val INVALID_SURNAME = 5
        const val INVALID_NICKNAME = 6
    }
}


