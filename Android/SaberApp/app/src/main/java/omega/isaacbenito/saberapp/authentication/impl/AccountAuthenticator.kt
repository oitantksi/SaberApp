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

package omega.isaacbenito.saberapp.authentication.impl

import android.accounts.AbstractAccountAuthenticator
import android.accounts.Account
import android.accounts.AccountAuthenticatorResponse
import android.accounts.AccountManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import omega.isaacbenito.saberapp.authentication.ui.AuthActivity
import javax.inject.Inject

/**
 * Mòdul per a gestionar el tipus especific de compte de l'aplicació.
 * Quan es vol afegir un compte al dispositiu des del gestor de comptes d'android l'AccountManager
 * del sistema crida a aquesta classe per a gestionar els comptes del tipus de l'aplicació.
 *
 * l'AccountAuthenticator gestiona les crides del sistema i deriva a l'activitat
 * d'autenticació en cas necessàri.
 *
 * Únicament s'han implementat els mètodes imprescindibles per a que el sistema reconegui
 * i pugui gestionar el tipus de compte de l'aplicació.
 */
class AccountAuthenticator(private val context: Context) : AbstractAccountAuthenticator(context) {

    companion object {
        private val TAG = this::class.java.name
    }

    @Inject
    lateinit var authManager: AuthenticationManagerImpl

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

        val intent = Intent(context, AuthActivity::class.java).apply {
            putExtra(AuthenticationManagerImpl.ARG_ACCOUNT_TYPE, accountType)
            putExtra(AuthenticationManagerImpl.ARG_AUTH_TYPE, authTokenType)
            putExtra(AuthenticationManagerImpl.ARG_IS_ADDING_NEW_ACCOUNT, true)
            putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response)
        }

        return Bundle().apply {
            putParcelable(AccountManager.KEY_INTENT, intent)
        }
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
//
//        val accountManager = AccountManager.get(context)
//
//        val accountMail = account?.name
//        val password = accountManager.getPassword(account)

        val authToken = authManager.getAuthToken()

        if (authToken.isNotEmpty()) {
            return Bundle().apply {
                putString(AccountManager.KEY_ACCOUNT_NAME, account?.name)
                putString(AccountManager.KEY_ACCOUNT_TYPE, account?.type)
                putString(AccountManager.KEY_AUTHTOKEN, authToken)
            }
        }

        val intent = Intent(context, AuthenticationManagerImpl::class.java).apply {
            putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response)
            putExtra(AuthenticationManagerImpl.ARG_ACCOUNT_TYPE, account?.type)
            putExtra(AuthenticationManagerImpl.ARG_AUTH_TYPE, authTokenType)
            putExtra(AuthenticationManagerImpl.ARG_ACCOUNT_NAME, account?.name)
        }

        return Bundle().apply {
            putParcelable(AccountManager.KEY_INTENT, intent)
        }
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