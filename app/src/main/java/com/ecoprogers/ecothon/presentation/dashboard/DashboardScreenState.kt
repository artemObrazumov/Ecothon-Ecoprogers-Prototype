package com.ecoprogers.ecothon.presentation.dashboard

import com.ecoprogers.ecothon.domain.models.Plant

sealed class DashboardScreenState {
    data object Loading: DashboardScreenState()
    data class Content(
        val plants: List<Plant>
    ): DashboardScreenState()
}