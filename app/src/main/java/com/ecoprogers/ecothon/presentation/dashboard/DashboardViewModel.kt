package com.ecoprogers.ecothon.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecoprogers.ecothon.data.PlantsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DashboardViewModel: ViewModel() {

    private val _state: MutableStateFlow<DashboardScreenState> =
        MutableStateFlow(DashboardScreenState.Loading)
    val state = _state.asStateFlow()

    init {
        loadDashboard()
    }

    private fun loadDashboard() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.emit(
                DashboardScreenState.Content(
                    PlantsApi.loadPlants()
                )
            )
        }
    }
}