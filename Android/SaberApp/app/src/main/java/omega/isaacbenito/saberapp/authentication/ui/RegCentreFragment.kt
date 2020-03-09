package omega.isaacbenito.saberapp.authentication.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import omega.isaacbenito.saberapp.R
import omega.isaacbenito.saberapp.authentication.model.RegCentreViewModel
import omega.isaacbenito.saberapp.authentication.model.RegisterViewModel
import omega.isaacbenito.saberapp.databinding.FragmentRegCentreBinding
import javax.inject.Inject


class RegCentreFragment : Fragment() {

    @Inject
    lateinit var viewModel: RegCentreViewModel

    @Inject lateinit var registerViewModel: RegisterViewModel

    private lateinit var binding: FragmentRegCentreBinding

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        registerViewModel.registrationStatus.observe(this, Observer {
            if(it is AuthSuccess) {
//                this.findNavController().navigate(R.id.action_regCentreFragment_to_navigation_root)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_reg_centre, container, false)

        viewManager = LinearLayoutManager(context)
        viewAdapter = CentreAdapter(
            viewModel.centres,
            AdapterView.OnItemClickListener { _: AdapterView<*>?, _: View?, position: Int, _: Long ->
                registerViewModel.updateCentreData(viewModel.centres[position])
            })

        recyclerView = binding.centreList.apply {
            setHasFixedSize(true)

            layoutManager = viewManager

            adapter = viewAdapter
        }

        val decoration = DividerItemDecoration(context, VERTICAL)
        recyclerView.addItemDecoration(decoration)

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (activity as AuthActivity).authComponent.inject(this)
    }

    class CentreAdapter(
        private val centreList: List<String>,
        val listener: AdapterView.OnItemClickListener
    ) : RecyclerView.Adapter<CentreAdapter.CentreVH>() {

        class CentreVH(val view: LinearLayout) : RecyclerView.ViewHolder(view) {
            val centreTextView = view.findViewById<TextView>(R.id.centreTextView)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CentreVH {
            val itemLayout = LayoutInflater.from(parent.context)
                .inflate(R.layout.reg_centre_item, parent, false) as LinearLayout
            return CentreVH(itemLayout)
        }

        override fun getItemCount(): Int = centreList.size

        override fun onBindViewHolder(holder: CentreVH, position: Int) {
            holder.centreTextView.text = centreList[position]
        }
    }
}



