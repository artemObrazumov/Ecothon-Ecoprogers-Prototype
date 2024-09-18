package com.ecoprogers.ecothon.presentation.plant_on_map_info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecoprogers.ecothon.data.PlantsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PlantOnMapInfoViewModel(
    private val id: String
): ViewModel() {

    private val _state: MutableStateFlow<PlantOnMapInfoScreenState> =
        MutableStateFlow(PlantOnMapInfoScreenState.Loading)
    val state = _state.asStateFlow()

    init {
        loadPlantInfo()
    }

    private fun loadPlantInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.emit(PlantOnMapInfoScreenState.Content(
                info = PlantsApi.loadPlantOnMapInfo(id)
            ))
        }
    }
}