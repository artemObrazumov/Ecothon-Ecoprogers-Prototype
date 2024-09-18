package com.ecoprogers.ecothon.presentation.plant

import com.ecoprogers.ecothon.domain.models.Plant

sealed class PlantScreenState {
    data object Loading: PlantScreenState()
    data class Content(
        val plant: Plant
    ): PlantScreenState()
}