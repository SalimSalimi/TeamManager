package fr.im.salimi.projectmanager.ui.functionForm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.util.Pair
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import fr.im.salimi.projectmanager.R
import fr.im.salimi.projectmanager.data.database.ProjectRoomDatabase
import fr.im.salimi.projectmanager.data.entities.Module
import fr.im.salimi.projectmanager.data.repositories.FunctionRepository
import fr.im.salimi.projectmanager.databinding.FunctionFormFragmentBinding
import fr.im.salimi.projectmanager.ui.uiUtils.BaseSpinnerAdapter
import fr.im.salimi.projectmanager.ui.uiUtils.FabButtonStates
import fr.im.salimi.projectmanager.ui.uiUtils.chooseDatePicker
import fr.im.salimi.projectmanager.ui.uiUtils.setFabBtnBehaviour
import java.util.*

class FunctionFormFragment : Fragment() {

    private val args: FunctionFormFragmentArgs by navArgs()
    private lateinit var binding: FunctionFormFragmentBinding
    private val viewModel: FunctionFormViewModel by viewModels {
        val database = ProjectRoomDatabase.getInstance(requireContext())
        val repository = FunctionRepository(database.functionDao())
        FunctionFormViewModelFactory(args.functionId, repository)
    }

    private lateinit var spinnerAdapter: ModuleSpinnerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.function_form_fragment, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewModel = viewModel
        initObservers()

        setFabBtnBehaviour(FabButtonStates.SECONDARY_STATE) {
            fabBtnClicked()
        }
    }

    private fun initObservers() {
        viewModel.dateClickEvent.observe(viewLifecycleOwner) {
            if (it)
                onDateChooser()
        }

        viewModel.addFabBtnClickEvent.observe(viewLifecycleOwner) {
            if (it) {
                viewModel.upsert()
                viewModel.onAddFabClickedEventFinished()
                this.findNavController().navigate(R.id.action_functionFormFragment_to_functionListFragment)
            }
        }
    }

    private fun onDateChooser() {
        val initValues = Pair<Long, Long>(
            viewModel.function.value?.startingDate?.time,
            viewModel.function.value?.endingDate?.time
        )
        chooseDatePicker(initValues, {
            val startingDate = Date(it.first ?: Date().time)
            val endingDate = Date(it.second ?: Date().time)
            viewModel.onChooseDate(startingDate, endingDate)
            viewModel.onDateClickedEventFinished()
            binding.invalidateAll()
        }, {
            viewModel.onDateClickedEventFinished()
        }, {
            viewModel.onDateClickedEventFinished()
        })
    }

    private fun fabBtnClicked() {
        viewModel.onAddFabBtnClickEvent()
    }

    inner class ModuleSpinnerAdapter(modulesList: List<Module>): BaseSpinnerAdapter<Module>(requireContext(), modulesList) {
        override fun onSetViews(item: Module, titleView: TextView, subTitleView: TextView, roundedLetter: TextView) {
            titleView.text = item.name
            subTitleView.text = item.description
            roundedLetter.visibility = View.GONE
        }
    }
}