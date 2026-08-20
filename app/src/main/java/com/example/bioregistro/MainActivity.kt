package com.example.bioregistro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.bioregistro.ui.screens.HomeScreen
import com.example.bioregistro.ui.screens.RegisterObservationScreen
import com.example.bioregistro.ui.theme.BioRegistroTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BioRegistroTheme {

                var currentScreen by remember {
                    mutableStateOf("home")
                }

                when (currentScreen) {

                    "home" -> {
                        HomeScreen(
                            onRegisterClick = {
                                currentScreen = "register"
                            },
                            onHistoryClick = {

                            }
                        )
                    }

                    "register" -> {
                        RegisterObservationScreen(
                            onBackClick = {
                                currentScreen = "home"
                            },
                            onSaveClick = {

                            }
                        )
                    }
                }
            }
        }
    }
}