package com.ecoprogers.ecothon.data

import com.ecoprogers.ecothon.domain.models.Plant
import com.ecoprogers.ecothon.domain.models.PlantOnMapInfo
import com.ecoprogers.ecothon.domain.models.PlantParameters

object Database {

    val PLANTS = listOf(
        Plant(
            id = "1",
            name = "title",
            image = "123",
            briefing = "Основная инфа",
            description = "Описание",
            facts = "",
            mapPoints = listOf("55.751225;37.62954;1_1")
        ),
        Plant(
            id = "2",
            name = "title",
            image = "123",
            briefing = "Основная инфа",
            description = "Описание\n\n\n\n\n\n\n\n\n\n\n\n\n",
            facts = "",
            mapPoints = listOf("55.751225;37.62954;1_1")
        ),
        Plant(
            id = "3",
            name = "title",
            image = "123",
            briefing = "Основная инфа",
            description = "Описание\n\n\n\n\n\n\n\n\n\n\n\n\n",
            facts = "",
            mapPoints = listOf("55.751225;37.62954;1_1")
        )
    )

    val ON_MAP_INFO = listOf(
        PlantOnMapInfo(
            id = "1",
            name = "title",
            parametersByYears = hashMapOf(
                "2018" to PlantParameters("123"),
                "2019" to PlantParameters("123"),
                "2020" to PlantParameters("123")
            ).toSortedMap()
        )
    )
}