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

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import omega.isaacbenito.saberapp.R
import omega.isaacbenito.saberapp.SaberApp
import omega.isaacbenito.saberapp.authentication.ui.AuthActivity


class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_start)

        supportActionBar?.hide()

        val authManager = (application as SaberApp).appComponent.authManager()

        Handler().postDelayed( {
            if (authManager.userIsLoggedIn()) {
                val intent = Intent(this, MainActivity::class.java)
                if (Build.VERSION.SDK_INT >= 21) {
                    val logo = findViewById<View>(R.id.start_applogo)
                    val options = ActivityOptions
                        .makeSceneTransitionAnimation(
                            this, logo,
                            resources.getString(R.string.start_login_logo_transition)
                        )
                    startActivity(intent, options.toBundle())
                } else {
                    startActivity(intent)
                }
            } else {
                startActivity(Intent(this, AuthActivity::class.java))
            }
        }, 500L)
    }
}