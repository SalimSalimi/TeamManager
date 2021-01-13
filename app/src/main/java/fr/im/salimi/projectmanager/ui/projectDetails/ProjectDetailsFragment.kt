package fr.im.salimi.projectmanager.ui.projectDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import fr.im.salimi.projectmanager.R
import fr.im.salimi.projectmanager.data.database.ProjectRoomDatabase
import fr.im.salimi.projectmanager.data.repositories.ProjectRepository
import fr.im.salimi.projectmanager.databinding.ProjectDetailsFragmentBinding
import fr.im.salimi.projectmanager.ui.uiUtils.setBackgroundColorText

class ProjectDetailsFragment : Fragment() {

    private val id = 1L
    private lateinit var binding: ProjectDetailsFragmentBinding
    private val viewModel: ProjectDetailsViewModel by viewModels {
        val projectDao = ProjectRoomDatabase.getInstance(requireContext()).projectDao()
        val repository = ProjectRepository(projectDao)
        ProjectDetailsViewModelFactory(1L, repository)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater ,R.layout.project_details_fragment, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        binding.viewModel = viewModel

        viewModel.getProjectIsDone.observe(viewLifecycleOwner) {
            if (it) {
                binding.roundedLetter.setBackgroundColorText(viewModel.project.value!!.name)
                viewModel.onGetProjectDone()
            }
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initObserversEvents()
    }

    private fun initObserversEvents() {
        viewModel.modulesClickEvent.observe(viewLifecycleOwner) {
            if (it) {
                val directions = ProjectDetailsFragmentDirections.actionProjectDetailsFragmentToModuleListFragment(id)
                this.findNavController().navigate(directions)
                viewModel.onModulesClicked()
            }
        }

        viewModel.functionsClickEvent.observe(viewLifecycleOwner) {
            if (it) {
                val directions = ProjectDetailsFragmentDirections.actionProjectDetailsFragmentToFunctionListFragment(id)
                this.findNavController().navigate(directions)
                viewModel.onFunctionsClicked()
            }
        }

        viewModel.tasksClickEvent.observe(viewLifecycleOwner) {
            if (it) {
                viewModel.onTasksClicked()
            }
        }

        viewModel.developersClickEvent.observe(viewLifecycleOwner) {
            if (it) {
                viewModel.onDevelopersClicked()
            }
        }
    }
}