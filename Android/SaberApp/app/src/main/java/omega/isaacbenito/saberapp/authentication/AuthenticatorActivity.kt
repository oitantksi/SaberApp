package omega.isaacbenito.saberapp.authentication

import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class AuthenticatorActivity() {

    private val TAG = this.javaClass.name.toString()

    companion object {
        const val ARG_ACCOUNT_TYPE = "ACCOUNT_TYPE"
        const val ARG_AUTH_TYPE = "AUTH_TYPE"
        const val ARG_ACCOUNT_NAME = "ACCOUNT_NAME"
        const val ARG_IS_ADDING_NEW_ACCOUNT = "IS_ADDING_ACCOUNT"
    }



//    private val prefs = context.getSharedPreferences(context.resources.getString(R.string.ACCOUNT),
//                                                     Context.MODE_PRIVATE)




    fun login(userMail: String, password: String) {
//        TODO Login function
        Log.d(TAG, "Called login for user: $userMail with password $password")
//        Account(userMail, ).also {}
    }

    fun register() {
//        TODO Register function
    }


}