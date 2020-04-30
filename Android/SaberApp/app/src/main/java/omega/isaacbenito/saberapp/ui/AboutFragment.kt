package omega.isaacbenito.saberapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy
import omega.isaacbenito.saberapp.R
import omega.isaacbenito.saberapp.databinding.FragmentPagerTopBinding


private val about_pages = listOf(
    R.layout.fragment_about_credits,
    R.layout.fragment_about_contribution,
    R.layout.fragment_about_license
)

private val about_titles = listOf(
    R.string.credits,
    R.string.contributing,
    R.string.licensing
)

/**
 * TODO
 *
 * @author Isaac Benito
 **/
class AboutFragment : Fragment() {

    private lateinit var binding: FragmentPagerTopBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pager_top, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.pager.adapter = AboutPagerAdapter(this)

        TabLayoutMediator(
            binding.topTabLayout, binding.pager,
            TabConfigurationStrategy { tab: TabLayout.Tab, position: Int ->
                tab.setText(about_titles.get(position)
                )
            }
        ).attach()
    }

}

class AboutPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = about_pages.size

    override fun createFragment(position: Int): Fragment {
        val fragment = PageFragment()
        val args = Bundle()
        args.putInt(PageFragment.PAGE, position)
        fragment.arguments = args
        return fragment
    }
}



class PageFragment: Fragment() {
    companion object {
        const val PAGE = "PAGE"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val page = arguments?.getInt(PAGE) ?: throw NullPointerException()

        return inflater.inflate(about_pages[page], container)
    }
}
