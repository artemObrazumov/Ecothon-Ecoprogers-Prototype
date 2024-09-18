package com.ecoprogers.ecothon.presentation

import android.os.Bundle
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.ecoprogers.ecothon.R
import com.ecoprogers.ecothon.presentation.camera.CameraScreen
import com.ecoprogers.ecothon.presentation.dashboard.DashboardScreen
import com.ecoprogers.ecothon.presentation.plant.PlantScreen
import com.ecoprogers.ecothon.presentation.plant_details.PlantDetailsScreen
import com.ecoprogers.ecothon.presentation.plant_on_map_info.PlantOnMapInfoScreen
import kotlinx.serialization.Serializable

@Composable
fun App(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        var showActionButton by remember {
            mutableStateOf(false)
        }
        navController.addOnDestinationChangedListener(
            listener = { _, _, _ ->
                showActionButton = navController.currentDestination?.route.toString() in listOf(
                    Dashboard.javaClass.toString().split(" ")[1],
                    PlantInfo.Companion::class.java.toString().split(" ")[1]
                )
            }
        )

        NavHost(
            navController = navController,
            startDestination = PlantOnMap("1")
        ) {
            composable<Dashboard> {
                DashboardScreen(
                    onPlantClicked = { navController.navigate(PlantInfo(it)) }
                )
            }
            composable<PlantInfo> {
                PlantScreen(
                    modifier = modifier,
                    plantId = (it.toRoute() as PlantInfo).id,
                    onOpenPlantOnMapInfo = {}
                )
            }
            composable<Camera>(
                enterTransition = { expandVertically() },
                exitTransition = { shrinkVertically() }
            ) {
                CameraScreen(
                    onPhotoTaken = {
                        navController.navigate(PlantDetails) {
                            popUpTo<Camera> { inclusive = true }
                        }
                    }
                )
            }
            composable<PlantDetails> {
                PlantDetailsScreen(
                    onGoBack = { navController.navigateUp() }
                )
            }
            composable<PlantOnMap> {
                PlantOnMapInfoScreen(
                    plantId = (it.toRoute() as PlantOnMap).id
                )
            }
        }

        AnimatedVisibility (
            showActionButton,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 48.dp, end = 24.dp),
            enter = slideInHorizontally { it*2 },
            exit = slideOutHorizontally { it*2 }
        ) {
            FloatingActionButton(
                onClick = { navController.navigate(Camera) },
                containerColor = MaterialTheme.colorScheme.secondary
            ) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_camera_alt_24),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Color.White)
                )
            }
        }
    }
}

@Serializable
data object Dashboard

@Serializable
data class PlantInfo(
    val id: String
)

@Serializable
data object Camera

@Serializable
data object PlantDetails

@Serializable
data class PlantOnMap(
    val id: String
)