package com.example.bioregistro.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bioregistro.data.model.BirdObservation
import com.example.bioregistro.util.ImageUtils

@Composable
fun ObservationHistoryScreen(
    observations: List<BirdObservation>,
    onBackClick: () -> Unit,
    onDeleteClick: (BirdObservation) -> Unit
) {

    /*
     * Guarda temporalmente el registro que
     * el usuario quiere eliminar.
     */
    var observationToDelete by remember {
        mutableStateOf<BirdObservation?>(null)
    }

    /*
     * DIÁLOGO DE CONFIRMACIÓN
     */
    observationToDelete?.let { observation ->

        AlertDialog(
            onDismissRequest = {
                observationToDelete = null
            },

            title = {
                Text(
                    text = "Eliminar avistamiento"
                )
            },

            text = {
                Text(
                    text =
                        "¿Deseas eliminar el registro de " +
                                "\"${observation.species}\"? " +
                                "Esta acción no se puede deshacer."
                )
            },

            confirmButton = {

                Button(
                    onClick = {

                        onDeleteClick(observation)

                        observationToDelete = null
                    }
                ) {

                    Text("Eliminar")
                }
            },

            dismissButton = {

                OutlinedButton(
                    onClick = {
                        observationToDelete = null
                    }
                ) {

                    Text("Cancelar")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        /*
         * TÍTULO
         */
        Text(
            text = "Mis avistamientos",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text =
                "Registros realizados desde la aplicación móvil.",

            style =
                MaterialTheme.typography.bodyMedium
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        /*
         * LISTA VACÍA
         */
        if (observations.isEmpty()) {

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),

                horizontalAlignment =
                    Alignment.CenterHorizontally,

                verticalArrangement =
                    Arrangement.Center
            ) {

                Text(
                    text = "🐦",
                    fontSize = 50.sp
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                Text(
                    text =
                        "Aún no hay avistamientos registrados.",

                    textAlign =
                        TextAlign.Center,

                    fontWeight =
                        FontWeight.Medium
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text =
                        "Registra una observación desde la pantalla principal.",

                    textAlign =
                        TextAlign.Center,

                    style =
                        MaterialTheme.typography.bodyMedium
                )
            }

        } else {

            /*
             * LISTA DE REGISTROS
             */
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),

                verticalArrangement =
                    Arrangement.spacedBy(12.dp)
            ) {

                items(
                    items = observations,
                    key = { observation ->
                        observation.id
                    }
                ) { observation ->

                    ObservationCard(
                        observation = observation,

                        onDeleteClick = {
                            observationToDelete =
                                observation
                        }
                    )
                }
            }
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        /*
         * REGRESAR
         */
        Button(
            onClick = onBackClick,
            modifier = Modifier.fillMaxWidth()
        ) {

            Text("Regresar al inicio")
        }
    }
}


/*
 * TARJETA DEL AVISTAMIENTO
 */
@Composable
private fun ObservationCard(
    observation: BirdObservation,
    onDeleteClick: () -> Unit
) {

    /*
     * CONVERTIR BASE64 A IMAGEN
     */
    val bitmap =
        remember(
            observation.imageBase64
        ) {

            ImageUtils.base64ToBitmap(
                observation.imageBase64
            )
        }

    Card(
        modifier = Modifier.fillMaxWidth(),

        elevation =
            CardDefaults.cardElevation(
                defaultElevation = 4.dp
            )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {

            /*
             * FOTO
             */
            bitmap?.let { image ->

                Image(
                    bitmap =
                        image.asImageBitmap(),

                    contentDescription =
                        "Fotografía de ${observation.species}",

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(190.dp),

                    contentScale =
                        ContentScale.Crop
                )

                Spacer(
                    modifier =
                        Modifier.height(14.dp)
                )
            }

            /*
             * ESPECIE
             */
            Text(
                text =
                    "🐦 ${observation.species}",

                fontSize =
                    21.sp,

                fontWeight =
                    FontWeight.Bold
            )

            Spacer(
                modifier =
                    Modifier.height(12.dp)
            )

            /*
             * CANTIDAD
             */
            Text(
                text =
                    "Cantidad: ${observation.quantity}"
            )

            /*
             * UBICACIÓN
             */
            Text(
                text =
                    "Ubicación: ${observation.location}"
            )

            /*
             * COORDENADAS
             */
            if (
                observation.latitude != 0.0 &&
                observation.longitude != 0.0
            ) {

                Spacer(
                    modifier =
                        Modifier.height(6.dp)
                )

                Text(
                    text =
                        "Latitud: %.6f".format(
                            observation.latitude
                        )
                )

                Text(
                    text =
                        "Longitud: %.6f".format(
                            observation.longitude
                        )
                )
            }

            Spacer(
                modifier =
                    Modifier.height(6.dp)
            )

            /*
             * FECHA
             */
            Text(
                text =
                    "Fecha: ${observation.date}"
            )

            /*
             * OBSERVACIONES
             */
            if (
                observation.observations.isNotBlank()
            ) {

                Spacer(
                    modifier =
                        Modifier.height(12.dp)
                )

                HorizontalDivider()

                Spacer(
                    modifier =
                        Modifier.height(12.dp)
                )

                Text(
                    text = "Observaciones",
                    fontWeight =
                        FontWeight.SemiBold
                )

                Spacer(
                    modifier =
                        Modifier.height(4.dp)
                )

                Text(
                    text =
                        observation.observations
                )
            }

            /*
             * SEPARADOR
             */
            Spacer(
                modifier =
                    Modifier.height(16.dp)
            )

            HorizontalDivider()

            Spacer(
                modifier =
                    Modifier.height(12.dp)
            )

            /*
             * ELIMINAR
             */
            OutlinedButton(
                onClick =
                    onDeleteClick,

                modifier =
                    Modifier.fillMaxWidth()
            ) {

                Text(
                    "Eliminar avistamiento"
                )
            }
        }
    }
}