package com.ecoprogers.ecothon.presentation.plant

import android.graphics.drawable.Icon
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ecoprogers.ecothon.R
import com.ecoprogers.ecothon.presentation.components.COLLAPSED_TOP_BAR_HEIGHT
import com.ecoprogers.ecothon.presentation.components.CollapsedTopBar
import com.ecoprogers.ecothon.presentation.components.EXPANDED_TOP_BAR_HEIGHT
import com.ecoprogers.ecothon.presentation.components.ExpandedTopBar
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.map.MapObject
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider

@Composable
fun PlantScreen(
    modifier: Modifier = Modifier,
    plantId: String,
    onOpenPlantOnMapInfo: (id: String) -> Unit
) {
    val viewModel: PlantViewModel = viewModel(
        key = plantId,
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                PlantViewModel(plantId) as T
        }
    )
    val listState = rememberLazyListState()
    val state by viewModel.state.collectAsState()
    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        PlantScreenContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            state = state,
            listState = listState,
            onOpenPlantOnMapInfo = onOpenPlantOnMapInfo
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlantScreenContent(
    modifier: Modifier,
    state: PlantScreenState,
    listState: LazyListState,
    onOpenPlantOnMapInfo: (id: String) -> Unit
) {
    val overlapHeightPx = with(LocalDensity.current) {
        EXPANDED_TOP_BAR_HEIGHT.toPx() - COLLAPSED_TOP_BAR_HEIGHT.toPx()
    }
    val isCollapsed: Boolean by remember {
        derivedStateOf {
            val isFirstItemHidden =
                listState.firstVisibleItemScrollOffset > overlapHeightPx
            isFirstItemHidden || listState.firstVisibleItemIndex > 0
        }
    }
    Box(
        modifier = modifier
    ) {
        when (state) {
            is PlantScreenState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            is PlantScreenState.Content -> {
                CompositionLocalProvider(LocalOverscrollConfiguration provides null) {
                    val mapView = remember {
                        mutableStateOf<MapView?>(null)
                    }
                    val context = LocalContext.current
                    Box {
                        CollapsedTopBar(
                            title = state.plant.name,
                            isCollapsed = isCollapsed
                        )
                        LazyColumn(
                            state = listState,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            item {
                                ExpandedTopBar(
                                    title = state.plant.name,
                                    image = state.plant.image
                                )
                            }
                            item { 
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 8.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(MaterialTheme.colorScheme.primary)
                                        .padding(8.dp)
                                ) {
                                    Column {
                                        Text(
                                            text = "Общие данные",
                                            style = MaterialTheme.typography.headlineSmall
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = state.plant.briefing,
                                            style = MaterialTheme.typography.bodyLarge
                                        )
                                    }
                                }
                            }
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 8.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(MaterialTheme.colorScheme.primary)
                                        .padding(8.dp)
                                ) {
                                    Column {
                                        Text(
                                            text = "Описание",
                                            style = MaterialTheme.typography.headlineSmall
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = state.plant.description,
                                            style = MaterialTheme.typography.bodyLarge
                                        )
                                    }
                                }
                            }
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 8.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(MaterialTheme.colorScheme.primary)
                                        .padding(8.dp)
                                ) {
                                    Column {
                                        Text(
                                            text = "Интересные факты",
                                            style = MaterialTheme.typography.headlineSmall
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = state.plant.facts,
                                            style = MaterialTheme.typography.bodyLarge
                                        )
                                    }
                                }
                            }
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 8.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(MaterialTheme.colorScheme.primary)
                                        .padding(8.dp)
                                ) {
                                    Column {
                                        Text(
                                            text = "На карте",
                                            style = MaterialTheme.typography.headlineSmall
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        AndroidView(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(240.dp),
                                            factory = { MapView(it) }
                                        ) {
                                            mapView.value = it
                                        }
                                    }
                                }
                            }
                            item {
                                Spacer(modifier = modifier.height(16.dp))
                            }
                        }
                    }
                    LaunchedEffect(null) {
                        snapshotFlow { mapView.value }.collect {
                            it?.let {
                                MapKitFactory.initialize(context)
                                MapKitFactory.getInstance().onStart()
                                it.onStart()
                                val firstMapPoint = state.plant.mapPoints
                                    .firstOrNull()?.toMapPoint()
                                if (firstMapPoint != null) {
                                    mapView.value!!.mapWindow.map.move(
                                        CameraPosition(
                                            Point(firstMapPoint.latitude, firstMapPoint.longitude),
                                            17.0f,
                                            0f,
                                            0f
                                        )
                                    )
                                }
                                val imageProvider =
                                    ImageProvider.fromResource(
                                        context,
                                        R.drawable.plant
                                    )
                                state.plant.mapPoints.forEach { stringPoint ->
                                    val mapPoint = stringPoint.toMapPoint()
                                    mapView.value!!.map.mapObjects
                                        .addPlacemark().apply {
                                            geometry = Point(mapPoint.latitude, mapPoint.longitude)
                                            setIcon(imageProvider)
                                            setIconStyle(
                                                IconStyle().apply { scale = 0.1f }
                                            )
                                            addTapListener { p0, p1 ->
                                                onOpenPlantOnMapInfo(mapPoint.id)
                                                true
                                            }
                                        }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun String.toMapPoint(): MapPoint {
    val items = this.split(";")
    return MapPoint(items[0].toDouble(), items[1].toDouble(), items[2])
}

data class MapPoint(
    val latitude: Double,
    val longitude: Double,
    val id: String
)