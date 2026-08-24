package com.example.bioregistro.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(
    observationCount: Int = 0,
    onRegisterClick: () -> Unit = {},
    onHistoryClick: () -> Unit = {}
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.background
            )
            .padding(24.dp)
    ) {

        Spacer(
            modifier = Modifier.height(26.dp)
        )

        /*
         * ENCABEZADO
         */
        Row(
            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(64.dp)
                    .background(
                        color =
                            MaterialTheme.colorScheme
                                .primaryContainer,
                        shape = CircleShape
                    ),

                contentAlignment =
                    Alignment.Center
            ) {

                Text(
                    text = "🐦",
                    fontSize = 34.sp
                )
            }

            Column(
                modifier =
                    Modifier.padding(
                        start = 16.dp
                    )
            ) {

                Text(
                    text = "BioRegistro",
                    fontSize = 30.sp,
                    fontWeight =
                        FontWeight.Bold,
                    color =
                        MaterialTheme
                            .colorScheme
                            .primary
                )

                Text(
                    text =
                        "Monitoreo científico de aves",
                    style =
                        MaterialTheme
                            .typography
                            .bodyMedium,
                    color =
                        MaterialTheme
                            .colorScheme
                            .onSurfaceVariant
                )
            }
        }

        Spacer(
            modifier = Modifier.height(34.dp)
        )

        /*
         * MENSAJE PRINCIPAL
         */
        Text(
            text =
                "Ayuda a registrar la biodiversidad de tu entorno",

            fontSize = 23.sp,

            fontWeight =
                FontWeight.SemiBold,

            color =
                MaterialTheme
                    .colorScheme
                    .onBackground
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text =
                "Cada observación contribuye al monitoreo de aves y al análisis de cambios ambientales.",

            style =
                MaterialTheme
                    .typography
                    .bodyLarge,

            color =
                MaterialTheme
                    .colorScheme
                    .onSurfaceVariant
        )

        Spacer(
            modifier = Modifier.height(30.dp)
        )

        /*
         * CONTADOR
         */
        Card(
            modifier =
                Modifier.fillMaxWidth(),

            shape =
                RoundedCornerShape(24.dp),

            colors =
                CardDefaults.cardColors(
                    containerColor =
                        MaterialTheme
                            .colorScheme
                            .primaryContainer
                ),

            elevation =
                CardDefaults.cardElevation(
                    defaultElevation = 2.dp
                )
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(26.dp),

                horizontalAlignment =
                    Alignment.CenterHorizontally
            ) {

                Text(
                    text =
                        "Avistamientos registrados",

                    style =
                        MaterialTheme
                            .typography
                            .titleMedium,

                    textAlign =
                        TextAlign.Center,

                    color =
                        MaterialTheme
                            .colorScheme
                            .onPrimaryContainer
                )

                Spacer(
                    modifier =
                        Modifier.height(10.dp)
                )

                Text(
                    text =
                        observationCount.toString(),

                    fontSize = 48.sp,

                    fontWeight =
                        FontWeight.Bold,

                    color =
                        MaterialTheme
                            .colorScheme
                            .primary
                )

                Text(
                    text =
                        if (observationCount == 1)
                            "observación"
                        else
                            "observaciones",

                    style =
                        MaterialTheme
                            .typography
                            .bodyMedium
                )
            }
        }

        Spacer(
            modifier = Modifier.height(30.dp)
        )

        /*
         * REGISTRAR
         */
        Button(
            onClick =
                onRegisterClick,

            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(58.dp),

            shape =
                RoundedCornerShape(16.dp),

            colors =
                ButtonDefaults.buttonColors(
                    containerColor =
                        MaterialTheme
                            .colorScheme
                            .primary
                )
        ) {

            Text(
                text =
                    "＋  Registrar avistamiento",

                fontSize = 16.sp,

                fontWeight =
                    FontWeight.SemiBold
            )
        }

        Spacer(
            modifier =
                Modifier.height(14.dp)
        )

        /*
         * HISTORIAL
         */
        OutlinedButton(
            onClick =
                onHistoryClick,

            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(58.dp),

            shape =
                RoundedCornerShape(16.dp)
        ) {

            Text(
                text =
                    "Ver mis registros",

                fontSize = 16.sp,

                fontWeight =
                    FontWeight.Medium
            )
        }

        Spacer(
            modifier =
                Modifier.weight(1f)
        )

        /*
         * PIE
         */
        Text(
            text =
                "BioRegistro • Ciencia ciudadana",

            modifier =
                Modifier.fillMaxWidth(),

            textAlign =
                TextAlign.Center,

            style =
                MaterialTheme
                    .typography
                    .bodySmall,

            color =
                MaterialTheme
                    .colorScheme
                    .onSurfaceVariant
        )

        Spacer(
            modifier =
                Modifier.height(12.dp)
        )
    }
}