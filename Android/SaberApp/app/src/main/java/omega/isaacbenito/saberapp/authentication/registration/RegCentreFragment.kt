package omega.isaacbenito.saberapp.authentication.registration

import android.content.Context
import androidx.fragment.app.Fragment
import javax.inject.Inject

class RegCentreFragment : Fragment() {

    @Inject lateinit var viewModel: RegCentreViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (activity as RegistrationActivity).registrationComponent.inject(this)
    }
}