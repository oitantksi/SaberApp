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
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import omega.isaacbenito.saberapp.R
import omega.isaacbenito.saberapp.authentication.AuthenticationManager
import omega.isaacbenito.saberapp.authentication.ui.AuthActivity
import omega.isaacbenito.saberapp.databinding.FragmentUserProfileBinding
import omega.isaacbenito.saberapp.ui.MainActivity
import omega.isaacbenito.saberapp.user.model.UserProfileViewModel
import javax.inject.Inject

class UserProfileFragment : Fragment() {

    @Inject
    lateinit var userProfileVM: UserProfileViewModel

    @Inject
    lateinit var authManager: AuthenticationManager

    private lateinit var binding: FragmentUserProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userProfileVM.user.observe(this, Observer {
            binding.userName.text = "${it.name} ${it.surname}"
            binding.userNickname.text = it.nickname
            binding.userSchool.text = it.school
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_profile, container, false)

        binding.usernameEdit.setOnClickListener {
            //TODO Edit name and surname
            Toast.makeText(context, "Not iplemented yet", Toast.LENGTH_SHORT).show()
        }

        binding.userNicknameEdit.setOnClickListener {
            //TODO Edit nickname
            Toast.makeText(context, "Not iplemented yet", Toast.LENGTH_SHORT).show()
        }

        binding.userSchoolEdit.setOnClickListener {
            //TODO Edit school
            Toast.makeText(context, "Not iplemented yet", Toast.LENGTH_SHORT).show()
        }

        binding.userPasswordChange.setOnClickListener {
            //TODO Change Password
            Toast.makeText(context, "Not iplemented yet", Toast.LENGTH_SHORT).show()
        }

        binding.logoutButton.setOnClickListener {
            authManager.logoutUser()
            startActivity(Intent(context, AuthActivity::class.java))
        }

        binding.unregisterButton.setOnClickListener {
            authManager.unregisterUser()
            startActivity(Intent(context, AuthActivity::class.java))
        }

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (activity as MainActivity).authManager.userComponent?.inject(this)
    }
}