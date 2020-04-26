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
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import omega.isaacbenito.saberapp.R
import omega.isaacbenito.saberapp.databinding.ActivityAuthBinding
import javax.inject.Inject


/**
 * @author Isaac Benito
 *
 * Activitat que gestiona el flux de registre i/o login de l'usuari.
 *
 */
class AuthActivity : AppCompatActivity(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    private lateinit var binding : ActivityAuthBinding

    private lateinit var navController: NavController

    override fun androidInjector(): AndroidInjector<Any?>? {
        return androidInjector
    }

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
        AndroidInjection.inject(this)

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





