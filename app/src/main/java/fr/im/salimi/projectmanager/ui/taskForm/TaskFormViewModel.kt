package fr.im.salimi.projectmanager.ui.taskForm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.im.salimi.projectmanager.data.entities.Task
import fr.im.salimi.projectmanager.data.repositories.TaskRepository
import kotlinx.coroutines.launch
import java.util.*

class TaskFormViewModel(private val id: Long, private val repository: TaskRepository) : ViewModel() {
    
    private val _task = MutableLiveData<Task>()
    val task: LiveData<Task>
        get() = _task

    private val _dateClickEvent = MutableLiveData<Boolean>()
    val dateClickEvent: LiveData<Boolean>
        get() = _dateClickEvent

    private val _addFabBtnClickEvent = MutableLiveData<Boolean>()
    val addFabBtnClickEvent: LiveData<Boolean>
        get() = _addFabBtnClickEvent

    init {
        initFunction()
        _addFabBtnClickEvent.value = false
        _dateClickEvent.value = false
    }

    fun onDateClickedEvent() {
        _dateClickEvent.value = true
    }

    fun onDateClickedEventFinished() {
        _dateClickEvent.value = false
    }

    fun onAddFabBtnClickEvent() {
        _addFabBtnClickEvent.value = true
    }

    fun onAddFabClickedEventFinished() {
        _addFabBtnClickEvent.value = false
    }

    fun upsert() {
        if (id == -1L)
            insert()
        else
            update()
    }

    fun onChooseDate(startingDate: Date, endingDate: Date) {
        _task.value?.startingDate = startingDate
        _task.value?.endingDate = endingDate
    }

    private fun initFunction() {
        if (id == -1L)
            _task.value = Task()
        else
            getTaskById()
    }

    private fun getTaskById() {
        viewModelScope.launch {
            _task.value = repository.getById(id)
        }
    }

    private fun insert() {
        viewModelScope.launch {
            repository.insert(_task.value!!)
        }
    }

    private fun update() {
        viewModelScope.launch {
            repository.update(_task.value!!)
        }
    }

}