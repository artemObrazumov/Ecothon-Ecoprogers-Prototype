package com.ecoprogers.ecothon.domain.models

import java.util.SortedMap

data class PlantOnMapInfo(
    val id: String,
    val name: String,
    val parametersByYears: SortedMap<String, PlantParameters>
)
