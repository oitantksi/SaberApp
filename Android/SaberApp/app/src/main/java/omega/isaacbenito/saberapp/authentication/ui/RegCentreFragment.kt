package omega.isaacbenito.saberapp.authentication.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
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
import omega.isaacbenito.saberapp.databinding.RegCentreItemBinding
import omega.isaacbenito.saberapp.ui.MainActivity
import javax.inject.Inject


class RegCentreFragment : Fragment(), OnItemClickListener {

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
                startActivity(Intent(context, MainActivity::class.java))
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
            viewModel.centres, this)

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

    override fun onItemClick(position: Int) {
            Toast.makeText(
                context,
                viewModel.centres[position],
                Toast.LENGTH_SHORT
            ).show()
//            registerViewModel.updateCentreData(viewModel.centres[position])
        }


    class CentreAdapter(
        private val centreList: List<String>,
        val listener: OnItemClickListener
    ) : RecyclerView.Adapter<CentreAdapter.CentreVH>() {


        class CentreVH(private val binding: RegCentreItemBinding) : RecyclerView.ViewHolder(view){
            init {
                binding.setClickListener {
                    //TODO
                }
            }

            fun setOnItemClickListener(position:Int, listener: OnItemClickListener) {
                centreTextView.setOnClickListener({listener.onItemClick(position)})
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CentreVH {
            return CentreVH(RegCentreItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
        }

        override fun getItemCount(): Int = centreList.size

        override fun onBindViewHolder(holder: CentreVH, position: Int) {
            holder.centreTextView.text = centreList[position]
            holder.setOnItemClickListener(position, listener)
        }

    }
}

interface OnItemClickListener {
    fun onItemClick(position: Int)
}
