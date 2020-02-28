package omega.isaacbenito.saberapp.authentication

import android.accounts.AccountAuthenticatorActivity
import android.accounts.AccountManager
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Group
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import kotlinx.android.synthetic.main.activity_authenticator.*
import kotlinx.android.synthetic.main.activity_authenticator.view.*
import omega.isaacbenito.saberapp.R
import omega.isaacbenito.saberapp.databinding.ActivityAuthenticatorBinding
import java.security.MessageDigest


class AuthenticatorActivity : AppCompatActivity() {

    private val TAG = this.javaClass.name.toString()

    companion object {
        val ARG_ACCOUNT_TYPE = "ACCOUNT_TYPE"
        val ARG_AUTH_TYPE = "AUTH_TYPE"
        val ARG_ACCOUNT_NAME = "ACCOUNT_NAME"
        val ARG_IS_ADDING_NEW_ACCOUNT = "IS_ADDING_ACCOUNT"
    }

    private lateinit var binding: ActivityAuthenticatorBinding

    private lateinit var emailTextView: TextView
    private lateinit var passwordTextView: TextView

    private lateinit var loginGroup: Group
    private lateinit var registerGroup: Group

    private lateinit var accountManager: AccountManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "launched")

        binding = DataBindingUtil.setContentView(this, R.layout.activity_authenticator)

        val layout = binding.authenticationLayout

        loginGroup = binding.loginGroup
        registerGroup = binding.registerGroup

        emailTextView = layout.accountMail
        passwordTextView = layout.accountPassword

        binding.newUser.setOnClickListener { switchToRegister() }
        binding.alreadyMember.setOnClickListener { switchToLogin() }

        accountManager = AccountManager.get(baseContext)

        var accountName = intent.getStringExtra(ARG_ACCOUNT_NAME)

        if (accountName != null && accountName.isNotBlank()) {
            emailTextView.setText(accountName)
        }
    }

    fun submit() {
        Log.d(TAG, "submit")
        val userMail = emailTextView.text.toString()
        val userPassword = hashPassword(passwordTextView.text.toString())

    }

    /**
     *
     *
     * @param password Cadena de text amb la contrassenya introduida per l'usuari
     * @return Cadena de text amb el hash de la contrasenya
     */
    private fun hashPassword(password: String) : String {
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(password.toByteArray())
        return digest.fold("", { str, it -> str + "%02x".format(it) })
    }

    fun switchToRegister() {
        Log.d(TAG, "switchToRegister")
        loginGroup.visibility = View.GONE
        registerGroup.visibility = View.VISIBLE
    }

    fun switchToLogin() {
        Log.d(TAG, "switchToLogin")
        registerGroup.visibility = View.GONE
        loginGroup.visibility = View.VISIBLE
    }

}