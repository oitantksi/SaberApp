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

package omega.isaacbenito.saberapp.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import omega.isaacbenito.saberapp.R
import omega.isaacbenito.saberapp.SaberApp
import omega.isaacbenito.saberapp.databinding.FragmentUserBinding
import omega.isaacbenito.saberapp.model.UserProfileViewModel
import javax.inject.Inject


class UserProfileFragment : Fragment() {

    @Inject lateinit var userProfileViewModel: UserProfileViewModel

    private lateinit var binding: FragmentUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userProfileViewModel.user.observe(this, Observer {
            binding.userName.text = it.name
            binding.userSchool.text = it.school
        })

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user, container, false)

        binding.preguntaDia.setOnClickListener {
            Toast.makeText(context, "Not iplemented yet", Toast.LENGTH_SHORT).show()
        }

        binding.preguntesAntigues.setOnClickListener {
            Toast.makeText(context, "Not iplemented yet", Toast.LENGTH_SHORT).show()
        }

        binding.revisarRespostes.setOnClickListener {
            Toast.makeText(context, "Not iplemented yet", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (activity as MainActivity).authManager.userComponent!!.inject(this)

    }
}
