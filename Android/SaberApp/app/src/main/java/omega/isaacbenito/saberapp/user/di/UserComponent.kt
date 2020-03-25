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

package omega.isaacbenito.saberapp.user.di

import dagger.Subcomponent
import omega.isaacbenito.saberapp.di.LoggedUserScope
import omega.isaacbenito.saberapp.user.ui.UserMainFragment
import omega.isaacbenito.saberapp.user.ui.UserProfileFragment

@LoggedUserScope
@Subcomponent(modules = [UserModule::class])
interface UserComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): UserComponent
    }

    // Classes that can be injected by this Component
    fun inject(fragment: UserMainFragment)
    fun inject(fragment: UserProfileFragment)
}