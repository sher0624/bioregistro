package com.example.bioregistro.tv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)

        setContent {

            BioRegistroTheme {

                /*
                 * Lista de avistamientos recibida desde Firestore.
                 *
                 * Usamos mutableStateOf con una lista completa
                 * para que Compose detecte el cambio y vuelva
                 * a dibujar la pantalla.
                 */
                var observations by remember {
                    mutableStateOf<List<BirdObservation>>(
                        emptyList()
                    )
                }

                /*
                 * Repositorio de Firebase.
                 */
                val repository = remember {
                    ObservationRepository()
                }

                /*
                 * Listener en tiempo real.
                 *
                 * Cada vez que:
                 * - se guarda un registro,
                 * - se elimina,
                 * - cambia una fotografía,
                 *
                 * Firestore manda nuevamente la lista.
                 */
                DisposableEffect(Unit) {

                    val listener =
                        repository.listenObservations(

                            onChange = { firebaseObservations ->

                                /*
                                 * Muy importante:
                                 *
                                 * reemplazamos la lista completa.
                                 * Esto obliga a Compose a recomponer
                                 * TvDashboardScreen.
                                 */
                                observations =
                                    firebaseObservations
                            },

                            onError = { exception ->

                                /*
                                 * Si Firestore genera un error,
                                 * aparecerá en Logcat.
                                 */
                                exception.printStackTrace()
                            }
                        )

                    /*
                     * Cuando la Activity se destruye,
                     * dejamos de escuchar Firestore.
                     */
                    onDispose {

                        listener.remove()
                    }
                }

                /*
                 * Pantalla principal de TV.
                 */
                Surface(
                    modifier =
                        Modifier.fillMaxSize(),

                    shape =
                        RectangleShape
                ) {

                    TvDashboardScreen(
                        observations =
                            observations
                    )
                }
            }
        }
    }
}