package fr.im.salimi.projectmanager.ui.featureList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import fr.im.salimi.projectmanager.data.entities.Feature
import fr.im.salimi.projectmanager.data.repositories.FeatureRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

class FeatureListViewModel(private val projectId: Long, private val featureRepository: FeatureRepository) : ViewModel() {

    private val _featuresList: Flow<List<Feature>> = flow {
        if (projectId == -1L)
            emitAll(featureRepository.getAll())
        else
            emitAll(featureRepository.geAllProjectById(projectId))
    }

    val featuresList: LiveData<List<Feature>>
        get() = _featuresList.asLiveData()

    private val _navigateToFeatureFormEvent = MutableLiveData<Boolean>()
    val navigateToFeatureFormEvent: LiveData<Boolean>
        get() = _navigateToFeatureFormEvent

    init {
        _navigateToFeatureFormEvent.value = false
    }

    fun navigateToFeatureFormEventTriggered() {
        _navigateToFeatureFormEvent.value = true
    }

    fun navigateToFeatureFormEventDone() {
        _navigateToFeatureFormEvent.value = false
    }
}