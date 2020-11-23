package fr.im.salimi.projectmanager.ui.developerList

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import fr.im.salimi.projectmanager.R
import fr.im.salimi.projectmanager.data.database.ProjectRoomDatabase
import fr.im.salimi.projectmanager.data.entities.Developer
import fr.im.salimi.projectmanager.data.repositories.DeveloperRepository
import fr.im.salimi.projectmanager.databinding.DeveloperListFragmentBinding

class DeveloperListFragment : Fragment() {

    private lateinit var binding: DeveloperListFragmentBinding
    private lateinit var myAdapter: DeveloperListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater ,R.layout.developer_list_fragment, container, false)
        initFragmentData()
        return binding.root
    }

    private fun initRecycler() {
        binding.developersList.apply {
            val linearLayoutManager = LinearLayoutManager(requireContext())
            myAdapter = DeveloperListAdapter()
            this.layoutManager = linearLayoutManager
            this.adapter = myAdapter
        }
    }

    private fun initFragmentData() {
        val database = ProjectRoomDatabase.getInstance(requireContext())
        val repository = DeveloperRepository(database.developerDao())

        val viewModel: DeveloperListViewModel by viewModels {
            DeveloperListViewModelFactory(repository)
        }
        initRecycler()
        viewModel.listDevelopers.observe(viewLifecycleOwner) {
            myAdapter.developers = it
            myAdapter.notifyDataSetChanged()
        }
    }
}