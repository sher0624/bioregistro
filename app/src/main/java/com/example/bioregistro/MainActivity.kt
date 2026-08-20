package com.example.bioregistro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.bioregistro.ui.screens.HomeScreen
import com.example.bioregistro.ui.theme.BioRegistroTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BioRegistroTheme {
                HomeScreen()
            }
        }
    }
}