package fr.im.salimi.projectmanager.ui.projectDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.im.salimi.projectmanager.data.entities.Project
import fr.im.salimi.projectmanager.data.repositories.ProjectRepository
import kotlinx.coroutines.launch

class ProjectDetailsViewModel(private val id: Long, private val projectRepository: ProjectRepository) : ViewModel() {

    private val _project = MutableLiveData<Project>()
    val project: LiveData<Project>
        get() = _project

    private val _getProjectIsDone = MutableLiveData<Boolean>()
    val getProjectIsDone: LiveData<Boolean>
        get() = _getProjectIsDone

    init {
        _getProjectIsDone.value = false
        initProject()
    }

    private fun initProject() {
        viewModelScope.launch {
            _project.value = projectRepository.getById(id)
            _getProjectIsDone.value = true
        }
    }

    fun onGetProjectDone() {
        _getProjectIsDone.value = false
    }
}