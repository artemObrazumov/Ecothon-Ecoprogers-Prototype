package com.ecoprogers.ecothon.presentation.plant_details

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ecoprogers.ecothon.R
import com.ecoprogers.ecothon.data.UserProgress
import com.ecoprogers.ecothon.presentation.PlantDetails
import com.ecoprogers.ecothon.presentation.plant.PlantScreenContent
import com.ecoprogers.ecothon.presentation.plant.PlantScreenState
import com.ecoprogers.ecothon.presentation.plant.PlantViewModel

@Composable
fun PlantDetailsScreen(
    modifier: Modifier = Modifier,
    onGoBack: () -> Unit
) {
    val viewModel: PlantDetailsScreenViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                PlantDetailsScreenViewModel() as T
        }
    )
    val state by viewModel.state.collectAsState()
    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        PlantDetailsScreenContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            state = state,
            onGoBack = onGoBack
        )
    }
}

@Composable
fun PlantDetailsScreenContent(
    modifier: Modifier = Modifier,
    state: PlantScreenState,
    onGoBack: () -> Unit
) {
    Box(
        modifier = modifier
    ) {
        var vis by remember {
            mutableStateOf(false)
        }
        when (state) {
            is PlantScreenState.Loading -> {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "Обработка фотографии...")
                }
            }
            is PlantScreenState.Content -> {
                AnimatedVisibility(
                    visible = vis,
                    enter = slideInVertically() + fadeIn()
                ) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(8.dp)
                    ) {
                        item(span = { GridItemSpan(2) }) {
                            Text(
                                text = "Вы нашли: ${state.plant.name}!",
                                style = MaterialTheme.typography.headlineMedium
                            )
                        }
                        item(span = { GridItemSpan(2) }) {
                            Text(
                                text = state.plant.description,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                        item(span = { GridItemSpan(2) }) {
                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(
                                    items = state.plant.images
                                ) {
                                    Image(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(8.dp))
                                            .height(200.dp)
                                            .aspectRatio(1.2f),
                                        painter = painterResource(id = it),
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop,
                                    )
                                }
                            }
                        }
                        item(span = { GridItemSpan(2) }) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Image(
                                    painter = painterResource(id = R.drawable.coin), 
                                    contentDescription = null
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = "Вы заработали 10 очков!")
                            }
                        }
                        item(span = { GridItemSpan(2) }) {
                            Button(
                                colors = ButtonDefaults.buttonColors()
                                    .copy(containerColor = MaterialTheme.colorScheme.secondary),
                                onClick = { onGoBack() }
                            ) {
                                Text(text = "Вернуться на главную")
                            }
                        }
                    }
                }
                LaunchedEffect(true) {
                    vis = true
                    UserProgress.userScore += 10
                }
            }
        }
    }
}