package omega.isaacbenito.saberapp.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import omega.isaacbenito.saberapp.R
import omega.isaacbenito.saberapp.SaberApp
import omega.isaacbenito.saberapp.authentication.ui.AuthActivity


class StartActivity : AppCompatActivity() {

    val TAG = this.javaClass.name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(this.javaClass.name, "launched")

        setContentView(R.layout.activity_start)

        supportActionBar?.hide()

        val authManager = (application as SaberApp).appComponent.authManager()

        //TODO Check if account is saved on device
        Handler().postDelayed( {
            if (authManager.userIsLoggedIn()) {
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                startActivity(Intent(this, AuthActivity::class.java))
            }
        }, 1000L)
    }
}