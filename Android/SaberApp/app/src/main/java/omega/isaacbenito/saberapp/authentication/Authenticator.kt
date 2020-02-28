package omega.isaacbenito.saberapp.authentication

import android.content.Context
import android.util.Log

class Authenticator() {

    private val TAG = this.javaClass.name.toString()


//    private val prefs = context.getSharedPreferences(context.resources.getString(R.string.ACCOUNT),
//                                                     Context.MODE_PRIVATE)




    fun login(userMail: String, password: String) {
//        TODO Login function
        Log.d(TAG, "Called login for user: $userMail with password $password")
    }

    fun register() {
//        TODO Register function
    }


}