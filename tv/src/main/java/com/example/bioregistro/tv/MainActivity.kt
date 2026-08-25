package com.example.bioregistro.tv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Surface
import com.example.bioregistro.tv.data.model.BirdObservation
import com.example.bioregistro.tv.data.repository.ObservationRepository
import com.example.bioregistro.tv.ui.screens.TvDashboardScreen
import com.example.bioregistro.tv.ui.theme.BioRegistroTheme

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalTvMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            BioRegistroTheme {

                val observations = remember {
                    mutableStateListOf<BirdObservation>()
                }

                val repository = remember {
                    ObservationRepository()
                }

                DisposableEffect(Unit) {

                    val listener = repository.listenObservations(

                        onChange = { firebaseObservations ->

                            observations.clear()
                            observations.addAll(firebaseObservations)
                        },

                        onError = { exception ->
                            exception.printStackTrace()
                        }
                    )

                    onDispose {
                        listener.remove()
                    }
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    shape = RectangleShape
                ) {

                    TvDashboardScreen(
                        observations = observations
                    )
                }
            }
        }
    }
}