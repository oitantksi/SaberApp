package omega.isaacbenito.saberapp.authentication.ui

import android.content.Context
import androidx.fragment.app.Fragment
import omega.isaacbenito.saberapp.authentication.model.RegCentreViewModel
import javax.inject.Inject

class RegCentreFragment : Fragment() {

    @Inject lateinit var viewModel: RegCentreViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (activity as AuthActivity).authComponent.inject(this)
    }
}