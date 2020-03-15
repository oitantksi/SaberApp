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

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import omega.isaacbenito.saberapp.R
import omega.isaacbenito.saberapp.databinding.FragmentUserMainBinding
import omega.isaacbenito.saberapp.ui.MainActivity
import omega.isaacbenito.saberapp.user.model.UserMainViewModel
import javax.inject.Inject


class UserMainFragment : Fragment() {

    @Inject
    lateinit var userMainViewModel: UserMainViewModel

    private lateinit var binding: FragmentUserMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userMainViewModel.user.observe(this, Observer {
            binding.userName.text = it.name
            binding.userSchool.text = it.school
        })

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_main, container, false)

        binding.profileLayout.setOnClickListener {
            this.findNavController().navigate(R.id.action_userMainFragment_to_userProfileFragment)
        }

        binding.preguntaDia.setOnClickListener {
            //TODO Navigate to prgunta del dia
            Toast.makeText(context, "Not iplemented yet", Toast.LENGTH_SHORT).show()
        }

        binding.preguntesAntigues.setOnClickListener {
            //TODO Navigate to preguntes antigues
            Toast.makeText(context, "Not iplemented yet", Toast.LENGTH_SHORT).show()
        }

        binding.revisarRespostes.setOnClickListener {
            //TODO Navigate to revisar respostes
            Toast.makeText(context, "Not iplemented yet", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (activity as MainActivity).authManager.userComponent?.inject(this)

    }
}
