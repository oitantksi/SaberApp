package omega.isaacbenito.saberapp.authentication

import android.accounts.AbstractAccountAuthenticator
import android.accounts.Account
import android.accounts.AccountAuthenticatorResponse
import android.accounts.AccountManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import omega.isaacbenito.saberapp.authentication.login.LoginActivity
import javax.inject.Inject


class AccountAuthenticator(context: Context) : AbstractAccountAuthenticator(context) {

    private val TAG = this.javaClass.name

    val context = context

    @Inject lateinit var server: ServerAuthenticate

    /**
     * Es crida quan l'usuari vol afegir un nou compte al dispositiu ja sigui des de la
     * pròpia aplicació o des de el gestor de comptes de la configuració del dispositiu.
     *
     * @param response
     * @param accountType
     * @param authTokenType
     * @param requiredFeatures
     * @param options
     * @return Bundle amb l'intent per a iniciar l'AuthenticatorActivity
     */
    override fun addAccount(
        response: AccountAuthenticatorResponse?,
        accountType: String?,
        authTokenType: String?,
        requiredFeatures: Array<out String>?,
        options: Bundle?
    ): Bundle {

        Log.d(TAG, "addAccount")

        val intent = Intent(context, LoginActivity::class.java)
        intent.putExtra(AuthenticationManager.ARG_ACCOUNT_TYPE, accountType);
        intent.putExtra(AuthenticationManager.ARG_AUTH_TYPE, authTokenType);
        intent.putExtra(AuthenticationManager.ARG_IS_ADDING_NEW_ACCOUNT, true);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
        val bundle = Bundle()
        bundle.putParcelable(AccountManager.KEY_INTENT, intent)
        return bundle
    }

    /**
    * Obté l'AuthToken de la compta de l'usuari de l'anterior vegada que s'hagi pogut realitzar
    * la connexió en aquest dispositiu.
    * En cas que no existeixi se li demanarà a l'usuari que inicii sessió.
    *
    * @param response
    * @param account
    * @param authTokenType
    * @param options
    * @return Bundle amb el nom, tipus de compte i authToken en cas que aquest ja existeixi
    * en el dispositiu, en cas contrari Bundle amb l'intent per a iniciar l'AuthenticatorActivity
    */
    override fun getAuthToken(
        response: AccountAuthenticatorResponse?,
        account: Account?,
        authTokenType: String?,
        options: Bundle?
    ): Bundle {

        Log.d(TAG, "getAuthToken")

        val accountManager = AccountManager.get(context)

        val accountMail = account?.name
        val password = accountManager.getPassword(account)
        //TODO LogIn Parameters
        val authToken = server.logInUser(accountMail!!, password)

        if (!authToken.isEmpty()) {
            val result = Bundle()
            result.putString(AccountManager.KEY_ACCOUNT_NAME, account?.name);
            result.putString(AccountManager.KEY_ACCOUNT_TYPE, account?.type);
            result.putString(AccountManager.KEY_AUTHTOKEN, authToken);
            return result;
        }

        val intent = Intent(context, AuthenticationManager.javaClass)
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response)
        intent.putExtra(AuthenticationManager.ARG_ACCOUNT_TYPE, account!!.type)
        intent.putExtra(AuthenticationManager.ARG_AUTH_TYPE, authTokenType)
        intent.putExtra(AuthenticationManager.ARG_ACCOUNT_NAME, account.name)
        val bundle = Bundle()
        bundle.putParcelable(AccountManager.KEY_INTENT, intent)
        return bundle
    }

    override fun getAuthTokenLabel(authTokenType: String?): String {
        throw UnsupportedOperationException()
    }

    override fun confirmCredentials(
        response: AccountAuthenticatorResponse?,
        account: Account?,
        options: Bundle?
    ): Bundle {
        throw UnsupportedOperationException()
    }

    override fun updateCredentials(
        response: AccountAuthenticatorResponse?,
        account: Account?,
        authTokenType: String?,
        options: Bundle?
    ): Bundle {
        throw UnsupportedOperationException()
    }

    override fun hasFeatures(
        response: AccountAuthenticatorResponse?,
        account: Account?,
        features: Array<out String>?
    ): Bundle {
        throw UnsupportedOperationException()
    }

    override fun editProperties(
        response: AccountAuthenticatorResponse?,
        accountType: String?
    ): Bundle {
        throw UnsupportedOperationException()
    }


}