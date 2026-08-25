package com.example.bioregistro.tv.ui.screens

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.Text
import com.example.bioregistro.tv.data.model.BirdObservation


@Composable
fun TvDashboardScreen(
    observations: List<BirdObservation>
) {

    /*
     * TOTAL DE AVISTAMIENTOS
     */
    val totalObservations =
        observations.size

    /*
     * TOTAL DE ESPECIES DISTINTAS
     */
    val totalSpecies =
        observations
            .map {
                it.species
                    .trim()
                    .lowercase()
            }
            .filter {
                it.isNotBlank()
            }
            .distinct()
            .size

    /*
     * ÚLTIMO REGISTRO
     */
    val latestSpecies =
        observations
            .firstOrNull()
            ?.species
            ?.ifBlank {
                "Sin datos"
            }
            ?: "Sin datos"

    /*
     * PANTALLA PRINCIPAL
     */
    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .background(
                    brush =
                        Brush.verticalGradient(
                            colors =
                                listOf(
                                    Color(0xFF071C13),
                                    Color(0xFF123C29),
                                    Color(0xFF1F6844)
                                )
                        )
                )
                .padding(
                    horizontal = 48.dp,
                    vertical = 28.dp
                )
    ) {

        Column(
            modifier =
                Modifier.fillMaxSize()
        ) {

            /*
             * ENCABEZADO
             */
            HeaderSection()

            Spacer(
                modifier =
                    Modifier.height(
                        22.dp
                    )
            )

            /*
             * TARJETAS DE RESUMEN
             */
            Row(
                modifier =
                    Modifier.fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.spacedBy(
                        18.dp
                    )
            ) {

                NatureStatCard(
                    modifier =
                        Modifier.weight(1f),

                    title =
                        "Avistamientos",

                    value =
                        totalObservations.toString(),

                    subtitle =
                        "Registros totales"
                )

                NatureStatCard(
                    modifier =
                        Modifier.weight(1f),

                    title =
                        "Especies",

                    value =
                        totalSpecies.toString(),

                    subtitle =
                        "Especies identificadas"
                )

                NatureStatCard(
                    modifier =
                        Modifier.weight(1f),

                    title =
                        "Último registro",

                    value =
                        latestSpecies,

                    subtitle =
                        "Avistamiento reciente"
                )
            }

            Spacer(
                modifier =
                    Modifier.height(
                        24.dp
                    )
            )

            /*
             * TÍTULO DE LA LISTA
             */
            Text(
                text =
                    "Últimos avistamientos",

                fontSize =
                    26.sp,

                fontWeight =
                    FontWeight.Bold,

                color =
                    Color(
                        0xFFF1FFF4
                    )
            )

            Spacer(
                modifier =
                    Modifier.height(
                        12.dp
                    )
            )

            /*
             * REGISTROS
             */
            if (
                observations.isEmpty()
            ) {

                EmptyStateCard()

            } else {

                LazyColumn(
                    modifier =
                        Modifier.fillMaxSize(),

                    verticalArrangement =
                        Arrangement.spacedBy(
                            14.dp
                        )
                ) {

                    items(
                        items =
                            observations.take(10),

                        /*
                         * Cada documento de Firestore tiene
                         * su propio ID.
                         */
                        key = { observation ->

                            observation.id
                        }
                    ) { observation ->

                        ObservationItem(
                            observation =
                                observation
                        )
                    }
                }
            }
        }
    }
}

/*
 * ============================================================
 * ENCABEZADO
 * ============================================================
 */

@Composable
private fun HeaderSection() {

    Column {

        Text(
            text =
                "BioRegistro TV",

            fontSize =
                38.sp,

            fontWeight =
                FontWeight.ExtraBold,

            color =
                Color(
                    0xFFF5FFF6
                )
        )

        Spacer(
            modifier =
                Modifier.height(
                    4.dp
                )
        )

        Text(
            text =
                "Monitoreo de aves y cambio climático",

            fontSize =
                19.sp,

            color =
                Color(
                    0xFFCBE9D2
                )
        )
    }
}

/*
 * ============================================================
 * TARJETAS SUPERIORES
 * ============================================================
 */

@Composable
private fun NatureStatCard(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    subtitle: String
) {

    Column(
        modifier =
            modifier
                .clip(
                    RoundedCornerShape(
                        20.dp
                    )
                )
                .background(
                    Color(
                        0xD9183D2A
                    )
                )
                .border(
                    width =
                        1.dp,

                    color =
                        Color(
                            0x668AC99A
                        ),

                    shape =
                        RoundedCornerShape(
                            20.dp
                        )
                )
                .padding(
                    18.dp
                )
    ) {

        Text(
            text =
                title,

            fontSize =
                16.sp,

            color =
                Color(
                    0xFFC7E8CF
                )
        )

        Spacer(
            modifier =
                Modifier.height(
                    8.dp
                )
        )

        Text(
            text =
                value,

            fontSize =
                27.sp,

            fontWeight =
                FontWeight.ExtraBold,

            color =
                Color.White,

            maxLines =
                1,

            overflow =
                TextOverflow.Ellipsis
        )

        Spacer(
            modifier =
                Modifier.height(
                    4.dp
                )
        )

        Text(
            text =
                subtitle,

            fontSize =
                14.sp,

            color =
                Color(
                    0xFFAED1B7
                )
        )
    }
}

/*
 * ============================================================
 * SIN AVISTAMIENTOS
 * ============================================================
 */

@Composable
private fun EmptyStateCard() {

    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(
                        20.dp
                    )
                )
                .background(
                    Color(
                        0xD9183D2A
                    )
                )
                .border(
                    width =
                        1.dp,

                    color =
                        Color(
                            0x668AC99A
                        ),

                    shape =
                        RoundedCornerShape(
                            20.dp
                        )
                )
                .padding(
                    26.dp
                )
    ) {

        Column {

            Text(
                text =
                    "Sin avistamientos registrados",

                fontSize =
                    23.sp,

                fontWeight =
                    FontWeight.Bold,

                color =
                    Color.White
            )

            Spacer(
                modifier =
                    Modifier.height(
                        8.dp
                    )
            )

            Text(
                text =
                    "Registra un ave desde la aplicación móvil y aparecerá automáticamente aquí.",

                fontSize =
                    17.sp,

                color =
                    Color(
                        0xFFCBE9D2
                    )
            )
        }
    }
}

/*
 * ============================================================
 * AVISTAMIENTO INDIVIDUAL
 * ============================================================
 */

@Composable
private fun ObservationItem(
    observation: BirdObservation
) {

    /*
     * IMPORTANTE:
     *
     * NO utilizamos remember para la fotografía.
     *
     * Cada vez que Firestore actualice la lista,
     * Compose vuelve a ejecutar esta función y utiliza
     * el imageBase64 actual.
     */
    val birdBitmap =
        decodeBase64Image(
            imageBase64 =
                observation.imageBase64
        )

    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(
                        20.dp
                    )
                )
                .background(
                    Color(
                        0xD9123121
                    )
                )
                .border(
                    width =
                        1.dp,

                    color =
                        Color(
                            0x557DC08D
                        ),

                    shape =
                        RoundedCornerShape(
                            20.dp
                        )
                )
                .padding(
                    18.dp
                ),

        horizontalArrangement =
            Arrangement.spacedBy(
                20.dp
            ),

        verticalAlignment =
            Alignment.CenterVertically
    ) {

        /*
         * ====================================================
         * FOTOGRAFÍA DEL AVE
         * ====================================================
         */

        if (
            birdBitmap != null
        ) {

            Image(
                bitmap =
                    birdBitmap.asImageBitmap(),

                contentDescription =
                    "Fotografía de ${observation.species}",

                modifier =
                    Modifier
                        .width(
                            210.dp
                        )
                        .height(
                            150.dp
                        )
                        .clip(
                            RoundedCornerShape(
                                16.dp
                            )
                        ),

                contentScale =
                    ContentScale.Crop
            )

        } else {

            /*
             * REGISTRO SIN FOTO
             */
            Box(
                modifier =
                    Modifier
                        .width(
                            210.dp
                        )
                        .height(
                            150.dp
                        )
                        .clip(
                            RoundedCornerShape(
                                16.dp
                            )
                        )
                        .background(
                            Color(
                                0xFF1D4D35
                            )
                        ),

                contentAlignment =
                    Alignment.Center
            ) {

                Column(
                    horizontalAlignment =
                        Alignment.CenterHorizontally
                ) {

                    Text(
                        text =
                            "🐦",

                        fontSize =
                            38.sp
                    )

                    Spacer(
                        modifier =
                            Modifier.height(
                                6.dp
                            )
                    )

                    Text(
                        text =
                            "Sin fotografía",

                        fontSize =
                            14.sp,

                        color =
                            Color(
                                0xFFCBE9D2
                            )
                    )
                }
            }
        }

        /*
         * ====================================================
         * DATOS DEL AVE
         * ====================================================
         */

        Column(
            modifier =
                Modifier.weight(
                    1f
                )
        ) {

            /*
             * ESPECIE
             */
            Text(
                text =
                    observation
                        .species
                        .ifBlank {
                            "Especie sin identificar"
                        },

                fontSize =
                    25.sp,

                fontWeight =
                    FontWeight.Bold,

                color =
                    Color.White
            )

            Spacer(
                modifier =
                    Modifier.height(
                        10.dp
                    )
            )

            /*
             * CANTIDAD
             */
            Text(
                text =
                    "Cantidad: ${observation.quantity}",

                fontSize =
                    17.sp,

                color =
                    Color(
                        0xFFD6F2DB
                    )
            )

            Spacer(
                modifier =
                    Modifier.height(
                        4.dp
                    )
            )

            /*
             * UBICACIÓN
             */
            Text(
                text =
                    "Ubicación: ${
                        observation
                            .location
                            .ifBlank {
                                "Sin ubicación"
                            }
                    }",

                fontSize =
                    17.sp,

                color =
                    Color(
                        0xFFD6F2DB
                    )
            )

            Spacer(
                modifier =
                    Modifier.height(
                        4.dp
                    )
            )

            /*
             * FECHA
             */
            Text(
                text =
                    "Fecha: ${
                        observation
                            .date
                            .ifBlank {
                                "Sin fecha"
                            }
                    }",

                fontSize =
                    17.sp,

                color =
                    Color(
                        0xFFD6F2DB
                    )
            )

            /*
             * GPS
             */
            if (
                observation.latitude != 0.0 ||
                observation.longitude != 0.0
            ) {

                Spacer(
                    modifier =
                        Modifier.height(
                            4.dp
                        )
                )

                Text(
                    text =
                        "GPS: %.5f, %.5f"
                            .format(
                                observation.latitude,
                                observation.longitude
                            ),

                    fontSize =
                        14.sp,

                    color =
                        Color(
                            0xFFAED1B7
                        )
                )
            }

            /*
             * OBSERVACIONES
             */
            if (
                observation
                    .observations
                    .isNotBlank()
            ) {

                Spacer(
                    modifier =
                        Modifier.height(
                            8.dp
                        )
                )

                Text(
                    text =
                        "Observaciones: ${observation.observations}",

                    fontSize =
                        16.sp,

                    color =
                        Color(
                            0xFFE9F8EB
                        ),

                    maxLines =
                        2,

                    overflow =
                        TextOverflow.Ellipsis
                )
            }
        }
    }
}

/*
 * ============================================================
 * CONVERTIR FOTOGRAFÍA DE BASE64 A BITMAP
 * ============================================================
 */

private fun decodeBase64Image(
    imageBase64: String
): Bitmap? {

    /*
     * SI EL REGISTRO NO TIENE FOTO
     */
    if (
        imageBase64
            .isBlank()
    ) {

        return null
    }

    return try {

        /*
         * BASE64 → BYTES
         */
        val imageBytes =
            Base64.decode(
                imageBase64,
                Base64.DEFAULT
            )

        /*
         * BYTES → BITMAP
         */
        BitmapFactory.decodeByteArray(
            imageBytes,
            0,
            imageBytes.size
        )

    } catch (
        exception: Exception
    ) {

        exception.printStackTrace()

        null
    }
}