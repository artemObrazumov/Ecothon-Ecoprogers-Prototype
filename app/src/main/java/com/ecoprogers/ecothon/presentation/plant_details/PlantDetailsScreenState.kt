package com.ecoprogers.ecothon.presentation.plant_details

import com.ecoprogers.ecothon.domain.models.Plant

sealed class PlantDetailsScreenState {
    data object Loading: PlantDetailsScreenState()
    data class Content(
        val plantDetails: Plant
    ): PlantDetailsScreenState()
}