package edu.nd.pmcburne.hello

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import edu.nd.pmcburne.hello.AppDatabase



data class MainUIState(
    val selectedTag: String = "core",
    val locations: List<LocationEntity> = emptyList(),
    val allTags: List<String> = emptyList()
)

class MainViewModel(application: Application): AndroidViewModel(application){
    private val dao = AppDatabase.getDatabase(application).locationDao()
    private val repository = LocationRepository(dao)
    private val _uiState = MutableStateFlow(MainUIState())
    val uiState: StateFlow<MainUIState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.syncLocations()
            val allLocations = repository.getAllLocations()
            val filteredLocations = repository.getLocationsByTag("core")
            val tags = repository.getAllTags(allLocations)
            _uiState.value = MainUIState(selectedTag = "core", locations = filteredLocations, allTags = tags)
        }
    }
    fun selectTag(tag:String){
        viewModelScope.launch {
            val filteredLocations = repository.getLocationsByTag(tag)
            _uiState.value =_uiState.value.copy(selectedTag= tag, locations = filteredLocations)
        }
    }
}