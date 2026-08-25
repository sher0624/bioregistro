package com.example.bioregistro.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream
import kotlin.math.max

object ImageUtils {

    /**
     * Convierte un Bitmap a Base64.
     *
     * Primero reduce la resolución de la imagen para evitar
     * documentos demasiado grandes en Firestore.
     */
    fun bitmapToBase64(
        bitmap: Bitmap,
        maxDimension: Int = 800,
        quality: Int = 60
    ): String {

        val resizedBitmap =
            resizeBitmap(
                bitmap = bitmap,
                maxDimension = maxDimension
            )

        val outputStream =
            ByteArrayOutputStream()

        resizedBitmap.compress(
            Bitmap.CompressFormat.JPEG,
            quality,
            outputStream
        )

        val imageBytes =
            outputStream.toByteArray()

        return Base64.encodeToString(
            imageBytes,
            Base64.NO_WRAP
        )
    }

    /**
     * Convierte Base64 nuevamente a Bitmap.
     */
    fun base64ToBitmap(
        base64: String
    ): Bitmap? {

        if (base64.isBlank()) {
            return null
        }

        return try {

            val decodedBytes =
                Base64.decode(
                    base64,
                    Base64.DEFAULT
                )

            BitmapFactory.decodeByteArray(
                decodedBytes,
                0,
                decodedBytes.size
            )

        } catch (exception: Exception) {

            exception.printStackTrace()

            null
        }
    }

    /**
     * Reduce proporcionalmente la resolución del Bitmap.
     */
    private fun resizeBitmap(
        bitmap: Bitmap,
        maxDimension: Int
    ): Bitmap {

        val width =
            bitmap.width

        val height =
            bitmap.height

        val currentMax =
            max(
                width,
                height
            )

        if (currentMax <= maxDimension) {
            return bitmap
        }

        val scale =
            maxDimension.toFloat() /
                    currentMax.toFloat()

        val newWidth =
            (width * scale)
                .toInt()

        val newHeight =
            (height * scale)
                .toInt()

        return Bitmap.createScaledBitmap(
            bitmap,
            newWidth,
            newHeight,
            true
        )
    }
}