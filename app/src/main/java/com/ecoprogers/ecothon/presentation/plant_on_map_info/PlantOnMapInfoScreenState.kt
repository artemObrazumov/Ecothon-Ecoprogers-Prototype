package com.ecoprogers.ecothon.presentation.plant_on_map_info

import com.ecoprogers.ecothon.domain.models.PlantOnMapInfo

sealed class PlantOnMapInfoScreenState {
    data object Loading: PlantOnMapInfoScreenState()
    data class Content(
        val info: PlantOnMapInfo
    ): PlantOnMapInfoScreenState()
}