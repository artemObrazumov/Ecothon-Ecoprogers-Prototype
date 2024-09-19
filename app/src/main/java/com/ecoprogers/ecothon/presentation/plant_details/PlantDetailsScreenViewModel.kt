package com.ecoprogers.ecothon.presentation.plant_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecoprogers.ecothon.data.PlantsApi
import com.ecoprogers.ecothon.presentation.plant.PlantScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PlantDetailsScreenViewModel: ViewModel() {

    private val _state: MutableStateFlow<PlantScreenState> =
        MutableStateFlow(PlantScreenState.Loading)
    val state = _state.asStateFlow()

    init {
        loadPlant()
    }

    private fun loadPlant() {
        viewModelScope.launch(Dispatchers.IO) {
            delay(500L)
            _state.emit(
                PlantScreenState.Content(
                    PlantsApi.loadPlant("3")
                )
            )
        }
    }
}