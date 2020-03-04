package omega.isaacbenito.saberapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_reg_data.*
import omega.isaacbenito.saberapp.authentication.login.LoginActivity
import java.util.zip.Inflater

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(this.javaClass.name, "launched")

        setContentView(R.layout.activity_start)

        supportActionBar?.hide()

        Handler().postDelayed(Runnable {
            startActivity(Intent(this, LoginActivity::class.java))
        }, 2000L)
    }
}