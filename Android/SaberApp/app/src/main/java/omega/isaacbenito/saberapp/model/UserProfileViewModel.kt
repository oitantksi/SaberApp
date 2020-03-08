package omega.isaacbenito.saberapp.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import omega.isaacbenito.saberapp.di.LoggedUserScope
import omega.isaacbenito.saberapp.entities.User
import omega.isaacbenito.saberapp.user.UserRepository
import javax.inject.Inject

@LoggedUserScope
class UserProfileViewModel @Inject constructor(
    val userRepository: UserRepository
): ViewModel() {

    val user: LiveData<User> = userRepository.getUser()

}