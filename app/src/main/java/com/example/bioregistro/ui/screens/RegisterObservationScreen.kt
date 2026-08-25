package com.example.bioregistro.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.bioregistro.data.model.BirdObservation
import com.example.bioregistro.util.ImageUtils
import com.google.android.gms.location.LocationServices
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun RegisterObservationScreen(

    onBackClick: () -> Unit = {},

    onSaveClick:
        (BirdObservation) -> Unit
) {

    val context =
        LocalContext.current

    /*
     * GPS
     */
    val fusedLocationClient =
        remember {

            LocationServices
                .getFusedLocationProviderClient(
                    context
                )
        }

    /*
     * FORMULARIO
     */
    var species by remember {
        mutableStateOf("")
    }

    var quantity by remember {
        mutableStateOf("")
    }

    var location by remember {
        mutableStateOf("")
    }

    var observations by remember {
        mutableStateOf("")
    }

    /*
     * FECHA AUTOMÁTICA
     */
    var date by remember {

        mutableStateOf(
            SimpleDateFormat(
                "dd/MM/yyyy",
                Locale.getDefault()
            ).format(
                Date()
            )
        )
    }

    /*
     * COORDENADAS
     */
    var latitude by remember {
        mutableStateOf<Double?>(
            null
        )
    }

    var longitude by remember {
        mutableStateOf<Double?>(
            null
        )
    }

    /*
     * FOTOGRAFÍA
     */
    var selectedBitmap by remember {

        mutableStateOf<Bitmap?>(
            null
        )
    }

    var showPhotoDialog by remember {

        mutableStateOf(
            false
        )
    }

    /*
     * CÁMARA
     */
    val cameraLauncher =
        rememberLauncherForActivityResult(

            contract =
                ActivityResultContracts
                    .TakePicturePreview()

        ) { bitmap ->

            if (bitmap != null) {

                selectedBitmap =
                    bitmap
            }
        }

    /*
     * GALERÍA
     */
    val galleryLauncher =
        rememberLauncherForActivityResult(

            contract =
                ActivityResultContracts
                    .GetContent()

        ) { uri ->

            if (uri != null) {

                try {

                    selectedBitmap =

                        if (
                            Build.VERSION.SDK_INT >=
                            Build.VERSION_CODES.P
                        ) {

                            val source =
                                ImageDecoder
                                    .createSource(
                                        context
                                            .contentResolver,

                                        uri
                                    )

                            ImageDecoder
                                .decodeBitmap(
                                    source
                                ) { decoder, _, _ ->

                                    decoder.allocator =
                                        ImageDecoder
                                            .ALLOCATOR_SOFTWARE
                                }

                        } else {

                            @Suppress(
                                "DEPRECATION"
                            )

                            MediaStore
                                .Images
                                .Media
                                .getBitmap(

                                    context
                                        .contentResolver,

                                    uri
                                )
                        }

                } catch (
                    exception: Exception
                ) {

                    exception
                        .printStackTrace()
                }
            }
        }

    /*
     * PERMISO DE UBICACIÓN
     */
    val locationPermissionLauncher =
        rememberLauncherForActivityResult(

            contract =
                ActivityResultContracts
                    .RequestMultiplePermissions()

        ) { permissions ->

            val fineGranted =
                permissions[
                    Manifest.permission
                        .ACCESS_FINE_LOCATION
                ] ?: false

            val coarseGranted =
                permissions[
                    Manifest.permission
                        .ACCESS_COARSE_LOCATION
                ] ?: false

            if (
                fineGranted ||
                coarseGranted
            ) {

                val finePermission =
                    ContextCompat
                        .checkSelfPermission(

                            context,

                            Manifest.permission
                                .ACCESS_FINE_LOCATION
                        )

                val coarsePermission =
                    ContextCompat
                        .checkSelfPermission(

                            context,

                            Manifest.permission
                                .ACCESS_COARSE_LOCATION
                        )

                if (
                    finePermission ==
                    PackageManager
                        .PERMISSION_GRANTED ||

                    coarsePermission ==
                    PackageManager
                        .PERMISSION_GRANTED
                ) {

                    fusedLocationClient
                        .lastLocation

                        .addOnSuccessListener {
                                locationResult ->

                            if (
                                locationResult != null
                            ) {

                                latitude =
                                    locationResult
                                        .latitude

                                longitude =
                                    locationResult
                                        .longitude
                            }
                        }
                }
            }
        }

    /*
     * DIÁLOGO DE FOTOGRAFÍA
     */
    if (
        showPhotoDialog
    ) {

        AlertDialog(

            onDismissRequest = {

                showPhotoDialog =
                    false
            },

            title = {

                Text(
                    "Agregar fotografía"
                )
            },

            text = {

                Text(
                    "Selecciona cómo deseas agregar la fotografía del ave."
                )
            },

            confirmButton = {

                Button(
                    onClick = {

                        showPhotoDialog =
                            false

                        cameraLauncher
                            .launch(
                                null
                            )
                    }
                ) {

                    Text(
                        "Tomar foto"
                    )
                }
            },

            dismissButton = {

                OutlinedButton(
                    onClick = {

                        showPhotoDialog =
                            false

                        galleryLauncher
                            .launch(
                                "image/*"
                            )
                    }
                ) {

                    Text(
                        "Galería"
                    )
                }
            }
        )
    }

    /*
     * PANTALLA
     */
    Column(

        modifier =
            Modifier
                .fillMaxSize()
                .verticalScroll(
                    rememberScrollState()
                )
                .padding(
                    24.dp
                ),

        verticalArrangement =
            Arrangement
                .spacedBy(
                    16.dp
                )
    ) {

        Text(
            text =
                "Registrar avistamiento",

            fontSize =
                28.sp,

            fontWeight =
                FontWeight.Bold
        )

        Text(
            "Ingresa la información del ave observada."
        )

        /*
         * ESPECIE
         */
        OutlinedTextField(

            value =
                species,

            onValueChange = {
                species = it
            },

            label = {
                Text(
                    "Especie del ave"
                )
            },

            modifier =
                Modifier.fillMaxWidth(),

            singleLine =
                true
        )

        /*
         * CANTIDAD
         */
        OutlinedTextField(

            value =
                quantity,

            onValueChange = {
                    newValue ->

                if (
                    newValue
                        .all {
                                character ->

                            character
                                .isDigit()
                        }
                ) {

                    quantity =
                        newValue
                }
            },

            label = {

                Text(
                    "Cantidad"
                )
            },

            modifier =
                Modifier
                    .fillMaxWidth(),

            singleLine =
                true
        )

        /*
         * UBICACIÓN
         */
        OutlinedTextField(

            value =
                location,

            onValueChange = {

                location =
                    it
            },

            label = {

                Text(
                    "Ubicación"
                )
            },

            modifier =
                Modifier
                    .fillMaxWidth(),

            singleLine =
                true
        )

        /*
         * BOTÓN GPS
         */
        OutlinedButton(

            onClick = {

                val finePermission =
                    ContextCompat
                        .checkSelfPermission(

                            context,

                            Manifest.permission
                                .ACCESS_FINE_LOCATION
                        )

                val coarsePermission =
                    ContextCompat
                        .checkSelfPermission(

                            context,

                            Manifest.permission
                                .ACCESS_COARSE_LOCATION
                        )

                if (
                    finePermission ==
                    PackageManager
                        .PERMISSION_GRANTED ||

                    coarsePermission ==
                    PackageManager
                        .PERMISSION_GRANTED
                ) {

                    fusedLocationClient
                        .lastLocation

                        .addOnSuccessListener {
                                result ->

                            if (
                                result != null
                            ) {

                                latitude =
                                    result.latitude

                                longitude =
                                    result.longitude
                            }
                        }

                } else {

                    locationPermissionLauncher
                        .launch(

                            arrayOf(

                                Manifest.permission
                                    .ACCESS_FINE_LOCATION,

                                Manifest.permission
                                    .ACCESS_COARSE_LOCATION
                            )
                        )
                }
            },

            modifier =
                Modifier
                    .fillMaxWidth()
        ) {

            Text(
                "Obtener ubicación actual"
            )
        }

        /*
         * LATITUD
         */
        latitude?.let {
                latitudeValue ->

            Text(
                text =
                    "Latitud: %.6f"
                        .format(
                            latitudeValue
                        )
            )
        }

        /*
         * LONGITUD
         */
        longitude?.let {
                longitudeValue ->

            Text(
                text =
                    "Longitud: %.6f"
                        .format(
                            longitudeValue
                        )
            )
        }

        /*
         * FECHA
         */
        OutlinedTextField(

            value =
                date,

            onValueChange = {
                date = it
            },

            label = {

                Text(
                    "Fecha"
                )
            },

            modifier =
                Modifier
                    .fillMaxWidth(),

            singleLine =
                true
        )

        /*
         * OBSERVACIONES
         */
        OutlinedTextField(

            value =
                observations,

            onValueChange = {

                observations =
                    it
            },

            label = {

                Text(
                    "Observaciones"
                )
            },

            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(
                        120.dp
                    )
        )

        /*
         * VISTA PREVIA
         */
        selectedBitmap?.let {
                bitmap ->

            Text(
                text =
                    "Fotografía seleccionada",

                fontWeight =
                    FontWeight.SemiBold
            )

            Image(

                bitmap =
                    bitmap
                        .asImageBitmap(),

                contentDescription =
                    "Fotografía del ave",

                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(
                            220.dp
                        ),

                contentScale =
                    ContentScale.Crop
            )
        }

        /*
         * SELECCIONAR FOTO
         */
        OutlinedButton(

            onClick = {

                showPhotoDialog =
                    true
            },

            modifier =
                Modifier
                    .fillMaxWidth()
        ) {

            Text(

                if (
                    selectedBitmap ==
                    null
                ) {

                    "Seleccionar fotografía"

                } else {

                    "Cambiar fotografía"
                }
            )
        }

        Spacer(
            modifier =
                Modifier
                    .height(
                        8.dp
                    )
        )

        /*
         * GUARDAR
         */
        Button(

            onClick = {

                /*
                 * CONVERTIR FOTO A BASE64
                 */
                val imageBase64 =

                    selectedBitmap
                        ?.let {
                                bitmap ->

                            ImageUtils
                                .bitmapToBase64(
                                    bitmap
                                )
                        }
                        ?: ""

                /*
                 * CREAR REGISTRO
                 */
                val birdObservation =
                    BirdObservation(

                        species =
                            species
                                .trim(),

                        quantity =
                            quantity
                                .toIntOrNull()
                                ?: 0,

                        location =
                            location
                                .trim(),

                        latitude =
                            latitude
                                ?: 0.0,

                        longitude =
                            longitude
                                ?: 0.0,

                        date =
                            date
                                .trim(),

                        observations =
                            observations
                                .trim(),

                        imageBase64 =
                            imageBase64
                    )

                onSaveClick(
                    birdObservation
                )
            },

            modifier =
                Modifier
                    .fillMaxWidth(),

            enabled =

                species
                    .isNotBlank() &&

                        quantity
                            .isNotBlank() &&

                        quantity
                            .toIntOrNull() !=
                        null &&

                        (
                                quantity
                                    .toIntOrNull()
                                    ?: 0
                                ) > 0 &&

                        location
                            .isNotBlank() &&

                        latitude !=
                        null &&

                        longitude !=
                        null &&

                        date
                            .isNotBlank()
        ) {

            Text(
                "Guardar avistamiento"
            )
        }

        /*
         * REGRESAR
         */
        OutlinedButton(

            onClick =
                onBackClick,

            modifier =
                Modifier
                    .fillMaxWidth()
        ) {

            Text(
                "Regresar"
            )
        }
    }
}