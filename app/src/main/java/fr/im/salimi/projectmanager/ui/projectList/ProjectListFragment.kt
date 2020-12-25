package fr.im.salimi.projectmanager.ui.projectList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import fr.im.salimi.projectmanager.R
import fr.im.salimi.projectmanager.data.database.ProjectRoomDatabase
import fr.im.salimi.projectmanager.data.repositories.ProjectRepository
import fr.im.salimi.projectmanager.databinding.ProjectListFragmentBinding

class ProjectListFragment : Fragment() {

    private lateinit var binding: ProjectListFragmentBinding
    private lateinit var myAdapter: ProjectListAdapter

    private val viewModel: ProjectListViewModel by viewModels {
        val database = ProjectRoomDatabase.getInstance(requireContext())
        val repository = ProjectRepository(database.projectDao())
        ProjectListViewModelFactory(repository)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.project_list_fragment, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initObservers()
        initAdapter()
    }

    private fun initObservers() {
        viewModel.projectsList.observe(viewLifecycleOwner) {
            myAdapter.submitList(it)
        }
    }

    private fun initAdapter() {
        binding.projectsList.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            myAdapter = ProjectListAdapter()
            this.adapter = myAdapter
        }
    }

}