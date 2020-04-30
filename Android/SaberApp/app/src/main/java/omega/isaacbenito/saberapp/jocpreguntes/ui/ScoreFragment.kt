package omega.isaacbenito.saberapp.jocpreguntes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import dagger.android.support.DaggerFragment
import omega.isaacbenito.saberapp.R
import omega.isaacbenito.saberapp.databinding.FragmentScoreBinding
import omega.isaacbenito.saberapp.jocpreguntes.model.JocPreguntesVM
import omega.isaacbenito.saberapp.jocpreguntes.ui.adapters.ClassificationAdapter
import omega.isaacbenito.saberapp.jocpreguntes.ui.adapters.MateriaScoreAdapter
import timber.log.Timber
import javax.inject.Inject

/**
 * TODO
 *
 * @author Isaac Benito
 **/
class ScoreFragment : DaggerFragment() {

    private lateinit var binding: FragmentScoreBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val jocVM by viewModels<JocPreguntesVM> { viewModelFactory }

    private lateinit var materiaScoreAdapter: MateriaScoreAdapter

    private lateinit var classificationAdapter: ClassificationAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_score, container, false)

        val tabLayout = binding.topTabLayout
        for (tabTitle in JocPreguntesVM.CLASSIFICATION_SCOPES) {
            tabLayout.addTab(tabLayout.newTab().setText(tabTitle))
        }

        binding.topTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {}

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabSelected(tab: TabLayout.Tab?) {
                jocVM.onClassificationScopeChanged(tab?.position ?: throw NullPointerException())
            }
        })

        jocVM.classification.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                classificationAdapter.submitList(it)
            }
        })

        jocVM.userPosition.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                binding.classificationList.scrollToPosition(it)
            }
        })

        classificationAdapter = ClassificationAdapter()

        binding.classificationList.let {
            it.setHasFixedSize(true)
            it.layoutManager = LinearLayoutManager(context)
            it.adapter = classificationAdapter
        }

        jocVM.userScoreByMateria.observe(viewLifecycleOwner, Observer {
            Timber.d("New materies value: $it")
            if (it != null) {
                materiaScoreAdapter.submitList(it)
            }
        })

        materiaScoreAdapter = MateriaScoreAdapter(object : MateriaScoreAdapter.Interaction {
            override fun onSelectMateria(materiaId: Long) {
                jocVM.onClassificationMateriaChanged(materiaId)
            }
        })

        binding.materiaList.let {
            it.setHasFixedSize(true)
            val layoutManager = LinearLayoutManager(context)
            layoutManager.orientation = LinearLayoutManager.HORIZONTAL
            it.layoutManager = layoutManager
            it.adapter = materiaScoreAdapter
        }

        return binding.root
    }
}