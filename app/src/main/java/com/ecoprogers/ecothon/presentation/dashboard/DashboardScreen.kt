package com.ecoprogers.ecothon.presentation.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ecoprogers.ecothon.R
import com.ecoprogers.ecothon.data.UserProgress
import com.ecoprogers.ecothon.domain.models.Plant
import com.ecoprogers.ecothon.presentation.components.CollapsedTopBar
import com.ecoprogers.ecothon.presentation.plant.PlantViewModel

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    onPlantClicked: (id: String) -> Unit
) {
    val viewModel: DashboardViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                DashboardViewModel() as T
        }
    )
    val state by viewModel.state.collectAsState()
    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        DashboardScreenContent(
            modifier = Modifier
                .padding(innerPadding),
            state = state,
            onPlantClicked
        )
    }
}

@Composable
fun DashboardScreenContent(
    modifier: Modifier = Modifier,
    state: DashboardScreenState,
    onPlantClicked: (id: String) -> Unit
) {
    Box(
        modifier = modifier
    ) {
        when (state) {
            is DashboardScreenState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            is DashboardScreenState.Content -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item(span = { GridItemSpan(2) }) {
                        CollapsedTopBar(
                            isCollapsed = true,
                            title = "Главная",
                            padding = PaddingValues(top = 16.dp, start = 8.dp, end = 0.dp)
                        )
                    }
                    item(span = { GridItemSpan(2) }) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                modifier = Modifier.size(48.dp),
                                painter = painterResource(id = R.drawable.user),
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Артём",
                                style = MaterialTheme.typography.headlineSmall
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(MaterialTheme.colorScheme.primary)
                                    .padding(8.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Image(
                                        painter = painterResource(id = R.drawable.coin),
                                        contentDescription = null
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = UserProgress.userScore.toString(),
                                        style = MaterialTheme.typography.headlineSmall)
                                }
                            }
                        }
                    }
                    item(span = { GridItemSpan(2) }) {
                        Text(
                            modifier = Modifier.padding(horizontal = 8.dp),
                            text = "Растения",
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }
                    items(
                        items = state.plants
                    ) {
                        PlantItem(it, onPlantClicked)
                    }
                }
            }
        }
    }
}

@Composable
fun PlantItem(
    plant: Plant,
    onPlantClicked: (id: String) -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.primary)
            .fillMaxWidth()
            .clickable {
                onPlantClicked(plant.id)
            },
    ) {
        Column {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                painter = painterResource(plant.images[0]),
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )
            Text(
                text = plant.name,
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.titleLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}