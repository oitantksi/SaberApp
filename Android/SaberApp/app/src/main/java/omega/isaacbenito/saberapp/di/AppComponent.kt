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

package omega.isaacbenito.saberapp.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import omega.isaacbenito.saberapp.SaberApp
import omega.isaacbenito.saberapp.authentication.di.AuthModule
import omega.isaacbenito.saberapp.data.di.DataModule
import omega.isaacbenito.saberapp.di.viewModel.ViewModelFactoryModule
import omega.isaacbenito.saberapp.di.worker.WorkerFactoryModule
import omega.isaacbenito.saberapp.jocpreguntes.di.JocPreguntesModule
import omega.isaacbenito.saberapp.user.di.UserModule
import omega.isaacbenito.saberapp.utils.NetworkUtils
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        ViewModelFactoryModule::class,
        WorkerFactoryModule::class,
        DataModule::class,
        AuthModule::class,
        UserModule::class,
        JocPreguntesModule::class
    ]
)
interface AppComponent : AndroidInjector<SaberApp> {

    @Component.Factory
    interface Factory {
        // With @BindsInstance, the Context passed in will be available in the graph
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun networkUtils(): NetworkUtils
}

