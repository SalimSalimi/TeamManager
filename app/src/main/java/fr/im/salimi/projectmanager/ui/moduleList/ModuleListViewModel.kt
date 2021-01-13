package fr.im.salimi.projectmanager.ui.moduleList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import fr.im.salimi.projectmanager.data.entities.Module
import fr.im.salimi.projectmanager.data.repositories.ModuleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

class ModuleListViewModel(private val projectId: Long, private val repository: ModuleRepository) :
    ViewModel() {

    private val _modules: Flow<List<Module>> = flow {
        if (projectId != -1L)
            emitAll(repository.getAllByProjectId(projectId))
        else
            emitAll(repository.getAll())
    }

    val modules: LiveData<List<Module>>
        get() = _modules.asLiveData()

    private val _navigateToModuleFormEvent = MutableLiveData<Boolean>()
    val navigateToModuleFormEvent
        get() = _navigateToModuleFormEvent

    init {
        _navigateToModuleFormEvent.value = false
    }

    fun onAddFabBtnClicked() {
        onNavigateToModuleFormEvent()
    }

    fun onNavigateToModuleFormEventTriggered() {
        _navigateToModuleFormEvent.value = false
    }

    private fun onNavigateToModuleFormEvent() {
        _navigateToModuleFormEvent.value = true
    }

    private fun initModules() {
        _modules
    }
}