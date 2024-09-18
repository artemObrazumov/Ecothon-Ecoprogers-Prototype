package com.ecoprogers.ecothon.presentation.plant

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecoprogers.ecothon.data.PlantsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PlantViewModel(
    private val id: String
): ViewModel() {

    private val _state: MutableStateFlow<PlantScreenState> =
        MutableStateFlow(PlantScreenState.Loading)
    val state = _state.asStateFlow()

    init {
        loadPlant()
    }

    private fun loadPlant() {
        viewModelScope.launch(Dispatchers.IO) {
            val plant = PlantsApi.loadPlant(id)
            _state.emit(PlantScreenState.Content(plant))
        }
    }
}