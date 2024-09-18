package com.ecoprogers.ecothon.domain.models

data class Plant(
    val id: String,
    val name: String,
    val image: String,
    val briefing: String,
    val description: String,
    val facts: String,
    val mapPoints: List<String>
)
