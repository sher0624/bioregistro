package com.example.bioregistro

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.bioregistro.data.model.BirdObservation
import com.example.bioregistro.data.repository.ObservationRepository
import com.example.bioregistro.ui.screens.HomeScreen
import com.example.bioregistro.ui.screens.ObservationHistoryScreen
import com.example.bioregistro.ui.screens.RegisterObservationScreen
import com.example.bioregistro.ui.theme.BioRegistroTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {

        super.onCreate(
            savedInstanceState
        )

        setContent {

            BioRegistroTheme {

                /*
                 * PANTALLA ACTUAL
                 */
                var currentScreen by remember {
                    mutableStateOf("home")
                }

                /*
                 * LISTA DE FIRESTORE
                 */
                val observations =
                    remember {

                        mutableStateListOf<
                                BirdObservation
                                >()
                    }

                /*
                 * REPOSITORIO
                 */
                val repository =
                    remember {

                        ObservationRepository()
                    }

                /*
                 * LISTENER DE FIRESTORE
                 */
                DisposableEffect(Unit) {

                    val listener =
                        repository
                            .listenObservations(

                                onChange = {
                                        firebaseObservations ->

                                    observations.clear()

                                    observations.addAll(
                                        firebaseObservations
                                    )
                                },

                                onError = {
                                        exception ->

                                    Toast
                                        .makeText(
                                            this@MainActivity,

                                            "Error al obtener registros: " +
                                                    exception.message,

                                            Toast.LENGTH_LONG
                                        )
                                        .show()
                                }
                            )

                    onDispose {

                        listener.remove()
                    }
                }

                /*
                 * NAVEGACIÓN
                 */
                when (currentScreen) {

                    /*
                     * INICIO
                     */
                    "home" -> {

                        HomeScreen(

                            observationCount =
                                observations.size,

                            onRegisterClick = {

                                currentScreen =
                                    "register"
                            },

                            onHistoryClick = {

                                currentScreen =
                                    "history"
                            }
                        )
                    }

                    /*
                     * REGISTRAR
                     */
                    "register" -> {

                        RegisterObservationScreen(

                            onBackClick = {

                                currentScreen =
                                    "home"
                            },

                            onSaveClick = {
                                    observation ->

                                repository
                                    .saveObservation(

                                        observation =
                                            observation,

                                        onSuccess = {

                                            Toast
                                                .makeText(
                                                    this@MainActivity,

                                                    "Avistamiento guardado correctamente",

                                                    Toast.LENGTH_SHORT
                                                )
                                                .show()

                                            currentScreen =
                                                "home"
                                        },

                                        onError = {
                                                exception ->

                                            Toast
                                                .makeText(
                                                    this@MainActivity,

                                                    "Error al guardar: " +
                                                            exception.message,

                                                    Toast.LENGTH_LONG
                                                )
                                                .show()
                                        }
                                    )
                            }
                        )
                    }

                    /*
                     * HISTORIAL
                     */
                    "history" -> {

                        ObservationHistoryScreen(

                            observations =
                                observations,

                            onBackClick = {

                                currentScreen =
                                    "home"
                            },

                            /*
                             * ELIMINAR REGISTRO
                             */
                            onDeleteClick = {
                                    observation ->

                                repository
                                    .deleteObservation(

                                        observationId =
                                            observation.id,

                                        onSuccess = {

                                            Toast
                                                .makeText(
                                                    this@MainActivity,

                                                    "Avistamiento eliminado",

                                                    Toast.LENGTH_SHORT
                                                )
                                                .show()
                                        },

                                        onError = {
                                                exception ->

                                            Toast
                                                .makeText(
                                                    this@MainActivity,

                                                    "Error al eliminar: " +
                                                            exception.message,

                                                    Toast.LENGTH_LONG
                                                )
                                                .show()
                                        }
                                    )
                            }
                        )
                    }
                }
            }
        }
    }
}