package omega.isaacbenito.saberapp.authentication

import android.R.attr
import android.accounts.AbstractAccountAuthenticator
import android.accounts.Account
import android.accounts.AccountAuthenticatorResponse
import android.accounts.AccountManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils


class AccountAuthenticator(context: Context) : AbstractAccountAuthenticator(context) {

    val mContext: Context = context

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
        val intent = Intent(mContext, AuthenticatorActivity::class.java)
        intent.putExtra(AuthenticatorActivity.ARG_ACCOUNT_TYPE, attr.accountType)
        intent.putExtra(AuthenticatorActivity.ARG_AUTH_TYPE, authTokenType)
        intent.putExtra(AuthenticatorActivity.ARG_IS_ADDING_NEW_ACCOUNT, true)
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response)
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
        val am = AccountManager.get(mContext)

        val password = am.getPassword(account)

        var authToken = ""
//TODO            authToken = ServerAuthenticate.userSignIn(account!!.name, password, authTokenType)

        // Si obtenim un autjToken el retornem
        if (!TextUtils.isEmpty(authToken)) {
            val result = Bundle()
            result.putString(AccountManager.KEY_ACCOUNT_NAME, account!!.name)
            result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type)
            result.putString(AccountManager.KEY_AUTHTOKEN, authToken)
            return result
        }

        // Si no hem pogut obtenir un token re-preguntem a l'usuari per les seves credencials
        val intent = Intent(mContext, AuthenticatorActivity::class.java)
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response)
        intent.putExtra(AuthenticatorActivity.ARG_ACCOUNT_TYPE, account!!.type)
        intent.putExtra(AuthenticatorActivity.ARG_AUTH_TYPE, authTokenType)
        val bundle = Bundle()
        bundle.putParcelable(AccountManager.KEY_INTENT, intent)
        return bundle
    }

    override fun getAuthTokenLabel(authTokenType: String?): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun confirmCredentials(
        response: AccountAuthenticatorResponse?,
        account: Account?,
        options: Bundle?
    ): Bundle {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateCredentials(
        response: AccountAuthenticatorResponse?,
        account: Account?,
        authTokenType: String?,
        options: Bundle?
    ): Bundle {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }



    override fun hasFeatures(
        response: AccountAuthenticatorResponse?,
        account: Account?,
        features: Array<out String>?
    ): Bundle {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun editProperties(response: AccountAuthenticatorResponse?, accountType: String?): Bundle {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }



}