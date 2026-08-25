package com.example.bioregistro.tv.ui.screens

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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
    val totalObservations = observations.size

    val totalSpecies = observations
        .map { it.species.trim() }
        .filter { it.isNotBlank() }
        .distinct()
        .size

    val latestSpecies = observations
        .firstOrNull()
        ?.species
        ?.ifBlank { "Sin datos" }
        ?: "Sin datos"

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF071C13),
                        Color(0xFF123C29),
                        Color(0xFF1F6844)
                    )
                )
            )
            .padding(
                horizontal = 48.dp,
                vertical = 32.dp
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            HeaderSection()

            Spacer(
                modifier = Modifier.height(26.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(18.dp)
            ) {

                NatureStatCard(
                    modifier = Modifier.weight(1f),
                    title = "Avistamientos",
                    value = totalObservations.toString(),
                    subtitle = "Registros totales"
                )

                NatureStatCard(
                    modifier = Modifier.weight(1f),
                    title = "Especies",
                    value = totalSpecies.toString(),
                    subtitle = "Especies identificadas"
                )

                NatureStatCard(
                    modifier = Modifier.weight(1f),
                    title = "Último registro",
                    value = latestSpecies,
                    subtitle = "Avistamiento reciente"
                )
            }

            Spacer(
                modifier = Modifier.height(30.dp)
            )

            Text(
                text = "Últimos avistamientos",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFF1FFF4)
            )

            Spacer(
                modifier = Modifier.height(14.dp)
            )

            if (observations.isEmpty()) {

                EmptyStateCard()

            } else {

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {

                    items(
                        items = observations.take(10),
                        key = { observation ->
                            observation.id
                        }
                    ) { observation ->

                        ObservationItem(
                            observation = observation
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun HeaderSection() {

    Column {

        Text(
            text = "BioRegistro TV",
            fontSize = 38.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color(0xFFF5FFF6)
        )

        Spacer(
            modifier = Modifier.height(5.dp)
        )

        Text(
            text = "Monitoreo de aves y cambio climático",
            fontSize = 19.sp,
            color = Color(0xFFCBE9D2)
        )
    }
}

@Composable
private fun NatureStatCard(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    subtitle: String
) {

    Column(
        modifier = modifier
            .clip(
                RoundedCornerShape(20.dp)
            )
            .background(
                Color(0xD9183D2A)
            )
            .border(
                width = 1.dp,
                color = Color(0x668AC99A),
                shape = RoundedCornerShape(20.dp)
            )
            .padding(20.dp)
    ) {

        Text(
            text = title,
            fontSize = 16.sp,
            color = Color(0xFFC7E8CF)
        )

        Spacer(
            modifier = Modifier.height(9.dp)
        )

        Text(
            text = value,
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.White,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(
            modifier = Modifier.height(5.dp)
        )

        Text(
            text = subtitle,
            fontSize = 14.sp,
            color = Color(0xFFAED1B7)
        )
    }
}

@Composable
private fun EmptyStateCard() {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(20.dp)
            )
            .background(
                Color(0xD9183D2A)
            )
            .border(
                width = 1.dp,
                color = Color(0x668AC99A),
                shape = RoundedCornerShape(20.dp)
            )
            .padding(26.dp)
    ) {

        Column {

            Text(
                text = "Sin avistamientos registrados",
                fontSize = 23.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "Registra un ave desde la aplicación móvil y aparecerá automáticamente aquí.",
                fontSize = 17.sp,
                color = Color(0xFFCBE9D2)
            )
        }
    }
}

@Composable
private fun ObservationItem(
    observation: BirdObservation
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(20.dp)
            )
            .background(
                Color(0xD9123121)
            )
            .border(
                width = 1.dp,
                color = Color(0x557DC08D),
                shape = RoundedCornerShape(20.dp)
            )
            .padding(20.dp)
    ) {

        Text(
            text = observation.species.ifBlank {
                "Especie sin identificar"
            },
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        Text(
            text = "Cantidad: ${observation.quantity}",
            fontSize = 18.sp,
            color = Color(0xFFD6F2DB)
        )

        Text(
            text = "Ubicación: ${
                observation.location.ifBlank {
                    "Sin ubicación"
                }
            }",
            fontSize = 18.sp,
            color = Color(0xFFD6F2DB)
        )

        Text(
            text = "Fecha: ${
                observation.date.ifBlank {
                    "Sin fecha"
                }
            }",
            fontSize = 18.sp,
            color = Color(0xFFD6F2DB)
        )

        if (observation.observations.isNotBlank()) {

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "Observaciones: ${observation.observations}",
                fontSize = 17.sp,
                color = Color(0xFFE9F8EB)
            )
        }
    }
}