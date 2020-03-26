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

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import androidx.recyclerview.widget.LinearLayoutManager
import omega.isaacbenito.saberapp.R
import omega.isaacbenito.saberapp.authentication.ui.AuthActivity
import omega.isaacbenito.saberapp.authentication.ui.AuthState
import omega.isaacbenito.saberapp.authentication.ui.CentreAdapter
import omega.isaacbenito.saberapp.data.entities.Centre
import omega.isaacbenito.saberapp.databinding.DialogEditProfileBinding
import omega.isaacbenito.saberapp.databinding.FragmentUserProfileBinding
import omega.isaacbenito.saberapp.ui.MainActivity
import omega.isaacbenito.saberapp.user.model.UserViewModel
import timber.log.Timber
import javax.inject.Inject

class UserProfileFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val userVM by viewModels<UserViewModel> { viewModelFactory }

    private lateinit var binding: FragmentUserProfileBinding

    private lateinit var editDialog: Dialog

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (activity as MainActivity).authManager.userComponent?.inject(this)

        userVM.start((activity as MainActivity).authManager.userMail)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userVM.user.observe(this, Observer {
            binding.user = userVM.user.value
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_profile, container, false)

        binding.viewModel = userVM

        userVM.editEvent.observe(viewLifecycleOwner, Observer {
            Timber.d("Edit code: $it")
            if (it == UserViewModel.EDIT_FINISHED) {
                editDialog.cancel()
            } else {
                showEditDialog(it)
            }
        })

        //TODO Relocate Unregister

        //        binding.unregisterButton.setOnClickListener {
        //            setUnauthObserver(this, userVM.unregisterUser())
        //        }

        return binding.root
    }

    private fun setUnauthObserver(owner: LifecycleOwner, authState: LiveData<AuthState>) {
        authState.observe(owner, Observer {
            startActivity(Intent(context, AuthActivity::class.java))
        })
    }

    private fun showEditDialog(dialogType: Int) {
        val editViewBinding = DataBindingUtil.inflate<DialogEditProfileBinding>(
            layoutInflater, R.layout.dialog_edit_profile, null, false
        )

        editViewBinding.firstEditText = ""
        editViewBinding.secondEditText = ""
        editViewBinding.viewModel = userVM

        editDialog = Dialog(context!!, R.style.EditProfileDialog).apply {
            setContentView(editViewBinding.root)
            window?.setGravity(Gravity.BOTTOM)
            window?.setBackgroundDrawable(
                ColorDrawable(
                    ContextCompat.getColor(
                        context,
                        R.color.transparent
                    )
                )
            )
            window?.setWindowAnimations(R.style.EditProfileDialog)
            window?.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
        }

        when (dialogType) {
            UserViewModel.EDIT_NAME -> {
                editViewBinding.editTitleFirst.text = resources.getString(R.string.edit_name)
                editViewBinding.firstEditText = userVM.user.value?.name
            }
            UserViewModel.EDIT_SURNAME -> {
                editViewBinding.editTitleFirst.text = resources.getString(R.string.edit_surname)
                editViewBinding.firstEditText = userVM.user.value?.surname
            }
            UserViewModel.EDIT_NICKNAME -> {
                editViewBinding.editTitleFirst.text = resources.getString(R.string.edit_nickname)
                editViewBinding.firstEditText = userVM.user.value?.nickname
            }
            UserViewModel.EDIT_MAIL -> {
                editViewBinding.editTitleFirst.text = resources.getString(R.string.edit_nickname)
                editViewBinding.firstEditText = userVM.user.value?.email
            }
            UserViewModel.EDIT_PASSWORD -> {
                editViewBinding.editTitleFirst.text = resources.getString(R.string.edit_password)
                editViewBinding.editTitleSecond.visibility = View.VISIBLE
                editViewBinding.editTitleSecond.text =
                    resources.getString(R.string.edit_password_repeat)
                editViewBinding.secondEditProfileEditText.visibility = View.VISIBLE
            }
            UserViewModel.EDIT_CENTRE -> {
                userVM.getCentres()

                editViewBinding.editProfileDone.visibility = View.INVISIBLE
                editViewBinding.firstEditProfileEditText.visibility = View.GONE
                editViewBinding.editTitleFirst.text = resources.getString(R.string.choose_centre)
                editViewBinding.editProfileScrollView.visibility = View.VISIBLE

                val constraints = ConstraintSet()
                constraints.clone(editViewBinding.editProfileLayout)
                constraints.connect(
                    R.id.edit_title_first, ConstraintSet.BOTTOM,
                    R.id.edit_profile_scrollView, ConstraintSet.TOP
                )
                constraints.connect(
                    R.id.edit_profile_cancel, ConstraintSet.BOTTOM,
                    R.id.edit_profile_layout, ConstraintSet.BOTTOM
                )
                constraints.applyTo(editViewBinding.editProfileLayout)

                val adapter = CentreAdapter(object : CentreAdapter.Interaction {
                    override fun onClickCentre(position: Int, centre: Centre) {
                        userVM.editUser(dialogType, centre.name)
                    }
                })

                editViewBinding.editProfileListView.let {
                    it.setHasFixedSize(true)
                    it.layoutManager = LinearLayoutManager(context)
                    it.adapter = adapter
                    it.addItemDecoration(DividerItemDecoration(context, VERTICAL))
                }

                userVM.centres.observe(viewLifecycleOwner, Observer {
                    adapter.submitList(it)
                })
            }
        }

        return editDialog.show()
    }



}