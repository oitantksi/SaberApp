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

package omega.isaacbenito.saberapp.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import omega.isaacbenito.saberapp.R
import omega.isaacbenito.saberapp.SaberApp
import omega.isaacbenito.saberapp.authentication.AuthenticationManager
import omega.isaacbenito.saberapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration

    lateinit var authManager: AuthenticationManager

    private val _tag = this.javaClass.name

    override fun onCreate(savedInstanceState: Bundle?) {
        authManager = (application as SaberApp).appComponent.authManager()

        super.onCreate(savedInstanceState)

        Log.d(_tag, "launched")

        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )

        drawerLayout = binding.drawerLayout

        val navController = this.findNavController(R.id.mainNavHostFragment)

        val toolbar = findViewById<Toolbar>(R.id.main_toolbar)
        setSupportActionBar(toolbar)

        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration)

        navController.addOnDestinationChangedListener { nc: NavController, nd: NavDestination, _: Bundle? ->
            if (nd.id == nc.graph.startDestination) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            } else {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
        }

        NavigationUI.setupWithNavController(binding.navView, navController)

//        navController.popBackStack()

    }


    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.mainNavHostFragment).navigateUp(appBarConfiguration)
    }

    override fun onBackPressed() {
        val navController = findNavController(R.id.mainNavHostFragment)
        if (navController.currentDestination?.id != navController.graph.startDestination) {
            super.onBackPressed()
        }
    }


}
