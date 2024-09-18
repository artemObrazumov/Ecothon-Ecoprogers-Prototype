package com.ecoprogers.ecothon.presentation.components

import android.graphics.drawable.GradientDrawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.ecoprogers.ecothon.R

@Composable
fun ExpandedTopBar(
    title: String = "",
    image: String = ""
) {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primary)
            .fillMaxWidth()
            .height(EXPANDED_TOP_BAR_HEIGHT),
        contentAlignment = Alignment.BottomStart
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.testimage),
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(COLLAPSED_TOP_BAR_HEIGHT * 1.4f)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Color.Transparent, Color.White),
                        start = Offset.Zero,
                        end = Offset(
                            0f,
                            with(LocalDensity.current) { COLLAPSED_TOP_BAR_HEIGHT.toPx() }
                        )
                    )
                )
        )
        Text(
            modifier = Modifier.padding(16.dp),
            text = title,
            color = Color.Black,
            style = MaterialTheme.typography.headlineLarge,
        )
    }
}

val COLLAPSED_TOP_BAR_HEIGHT = 64.dp
val EXPANDED_TOP_BAR_HEIGHT = 550.dp