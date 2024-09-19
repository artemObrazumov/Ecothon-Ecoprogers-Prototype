package com.ecoprogers.ecothon.presentation.plant_on_map_info

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ecoprogers.ecothon.R
import com.ecoprogers.ecothon.presentation.components.CollapsedTopBar

@Composable
fun PlantOnMapInfoScreen(
    modifier: Modifier = Modifier,
    plantId: String
) {
    val viewModel: PlantOnMapInfoViewModel = viewModel(
        key = plantId,
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                PlantOnMapInfoViewModel(plantId) as T
        }
    )
    val state by viewModel.state.collectAsState()
    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->

        PlantOnMapInfoScreenContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            state = state
        )
    }
}

@Composable
fun PlantOnMapInfoScreenContent(
    modifier: Modifier = Modifier,
    state: PlantOnMapInfoScreenState,
) {
    Box(modifier = modifier) {
        if (state is PlantOnMapInfoScreenState.Content) {
            var parameterId by remember {
                mutableStateOf(state.info.parametersByYears.firstNotNullOf { it.key })
            }
            val parameters = state.info.parametersByYears[parameterId]
            Column(
                modifier = Modifier
                    .requiredHeight(PlantOnMapPhotoSize),
                verticalArrangement = Arrangement.Bottom
            ) {
                CollapsedTopBar(
                    isCollapsed = true,
                    title = state.info.name,
                    padding = PaddingValues(top = 16.dp, start = 8.dp, end = 0.dp)
                )
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(8.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    painter = painterResource(id = parameters!!.photo),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                item { Spacer(modifier = Modifier.height(PlantOnMapPhotoSize)) }
                item {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        state.info.parametersByYears.forEach { (year, _) ->
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(
                                        if (parameterId == year) MaterialTheme.colorScheme.secondary
                                        else MaterialTheme.colorScheme.primary
                                    )
                                    .clickable {
                                        parameterId = year
                                    }
                                    .padding(8.dp)
                            ) {
                                Text(
                                    text = year,
                                    color = if (parameterId == year) Color.White
                                    else Color.Black,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colorScheme.primary)
                            .padding(8.dp)
                    ) {

                    }
                }
                item { Spacer(modifier = Modifier.height(PlantOnMapPhotoSize)) }
            }
        }
    }
}

val PlantOnMapPhotoSize = 500.dp