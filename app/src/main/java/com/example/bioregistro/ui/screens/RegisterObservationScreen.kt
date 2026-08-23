package com.example.bioregistro.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bioregistro.model.BirdObservation
import java.util.UUID

@Composable
fun RegisterObservationScreen(
    onBackClick: () -> Unit = {},
    onSaveClick: (BirdObservation) -> Unit = {}
) {

    var species by remember {
        mutableStateOf("")
    }

    var quantity by remember {
        mutableStateOf("")
    }

    var location by remember {
        mutableStateOf("")
    }

    var date by remember {
        mutableStateOf("")
    }

    var observations by remember {
        mutableStateOf("")
    }

    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    // Abre la galería del dispositivo para seleccionar una fotografía
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->

        if (uri != null) {
            imageUri = uri
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "Registrar avistamiento",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Ingresa la información del ave observada."
        )

        OutlinedTextField(
            value = species,
            onValueChange = {
                species = it
            },
            label = {
                Text("Especie del ave")
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = quantity,
            onValueChange = {
                quantity = it
            },
            label = {
                Text("Cantidad")
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = location,
            onValueChange = {
                location = it
            },
            label = {
                Text("Ubicación")
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = date,
            onValueChange = {
                date = it
            },
            label = {
                Text("Fecha")
            },
            placeholder = {
                Text("22/08/2026")
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = observations,
            onValueChange = {
                observations = it
            },
            label = {
                Text("Observaciones")
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        )

        OutlinedButton(
            onClick = {
                imagePickerLauncher.launch("image/*")
            },
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                text = if (imageUri == null) {
                    "Seleccionar fotografía"
                } else {
                    "Fotografía seleccionada ✓"
                }
            )
        }

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Button(
            onClick = {

                val observation = BirdObservation(
                    id = UUID.randomUUID().toString(),
                    species = species.trim(),
                    quantity = quantity.toIntOrNull() ?: 0,
                    location = location.trim(),
                    date = date.trim(),
                    observations = observations.trim(),
                    imageUri = imageUri?.toString() ?: "",
                    temperature = 0.0,
                    weather = ""
                )

                onSaveClick(observation)
            },
            modifier = Modifier.fillMaxWidth(),

            // El botón solamente se habilita cuando los datos obligatorios
            // y la fotografía han sido agregados.
            enabled =
                species.isNotBlank() &&
                        quantity.toIntOrNull() != null &&
                        quantity.toIntOrNull()!! > 0 &&
                        location.isNotBlank() &&
                        date.isNotBlank() &&
                        imageUri != null
        ) {

            Text(
                text = "Guardar avistamiento"
            )
        }

        OutlinedButton(
            onClick = onBackClick,
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                text = "Regresar"
            )
        }
    }
}