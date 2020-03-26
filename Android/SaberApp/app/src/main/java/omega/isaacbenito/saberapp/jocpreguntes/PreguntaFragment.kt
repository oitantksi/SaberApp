package omega.isaacbenito.saberapp.jocpreguntes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import omega.isaacbenito.saberapp.R
import omega.isaacbenito.saberapp.databinding.FragmentPreguntaBinding

class PreguntaFragment : Fragment() {

    private lateinit var binding: FragmentPreguntaBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pregunta, container, false)

        return binding.root
    }
}