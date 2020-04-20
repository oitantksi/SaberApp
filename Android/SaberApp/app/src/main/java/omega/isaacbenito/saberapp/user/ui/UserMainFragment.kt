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

package omega.isaacbenito.saberapp.user.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment
import omega.isaacbenito.saberapp.R
import omega.isaacbenito.saberapp.databinding.FragmentUserMainBinding
import omega.isaacbenito.saberapp.jocpreguntes.model.JocPreguntesViewModel
import omega.isaacbenito.saberapp.user.model.UserViewModel
import timber.log.Timber
import javax.inject.Inject


class UserMainFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val userVM by viewModels<UserViewModel> { viewModelFactory }
    private val jocVM by viewModels<JocPreguntesViewModel> { viewModelFactory }

    private lateinit var binding: FragmentUserMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userVM.user.observe(this, Observer {
            Timber.d(it?.toString() ?: "User is null")
            binding.user = it
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_main, container, false)

        userVM.snackbarMessage.observe(viewLifecycleOwner, Observer {
            Snackbar.make(binding.root, resources.getString(it), Snackbar.LENGTH_SHORT).show()
        })

        binding.viewModel = userVM
        binding.user = userVM.user.value

        binding.profileLayout.setOnClickListener {
            this.findNavController().navigate(R.id.action_userMainFragment_to_userProfileFragment)
        }

        binding.preguntaDia.setOnClickListener {
            jocVM.setup(JocPreguntesViewModel.ACTION_CURRENT)
            this.findNavController().navigate(
                UserMainFragmentDirections.actionUserMainFragmentToPreguntaFragment()
            )
        }

        binding.preguntesAntigues.setOnClickListener {
            jocVM.setup(JocPreguntesViewModel.ACTION_OLD)
            this.findNavController().navigate(
                UserMainFragmentDirections.actionUserMainFragmentToPreguntesFragment()
            )
        }

        binding.revisarRespostes.setOnClickListener {
            jocVM.setup(JocPreguntesViewModel.ACTION_REVIEW)
            this.findNavController().navigate(
                UserMainFragmentDirections.actionUserMainFragmentToPreguntesFragment()
            )
        }

        return binding.root
    }
}
