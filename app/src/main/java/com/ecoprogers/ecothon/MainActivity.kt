package com.ecoprogers.ecothon

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.ecoprogers.ecothon.presentation.App
import com.ecoprogers.ecothon.ui.theme.EcothonTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val cameraPermissionRequest =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) {}

        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CAMERA
            ) -> {}
            else -> {
                cameraPermissionRequest.launch(android.Manifest.permission.CAMERA)
            }
        }
        enableEdgeToEdge()
        setContent {
            EcothonTheme {
                App()
            }
        }
    }
}