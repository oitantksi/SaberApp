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

package omega.isaacbenito.saberapp.authentication.di

import dagger.Subcomponent
import dagger.android.AndroidInjector
import omega.isaacbenito.saberapp.authentication.ui.AuthActivity

/**
 * Component que injecta les dependències a tots els fragment i activitats del mòdul d'autenticació
 *
 * @author Isaac Benito
 */
@AuthScope
@Subcomponent(modules = [AuthBinds::class])
interface AuthComponent : AndroidInjector<AuthActivity> {

    @Subcomponent.Factory
    interface Factory: AndroidInjector.Factory<AuthActivity>
}
