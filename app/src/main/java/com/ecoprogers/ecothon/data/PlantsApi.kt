package com.ecoprogers.ecothon.data

import com.ecoprogers.ecothon.domain.models.Plant
import com.ecoprogers.ecothon.domain.models.PlantOnMapInfo

object PlantsApi {

    fun loadPlant(plantId: String): Plant = Database.PLANTS.first { it.id == plantId }

    fun loadPlants() = Database.PLANTS

    fun loadPlantOnMapInfo(id: String): PlantOnMapInfo = Database.ON_MAP_INFO.first { it.id == id }
}