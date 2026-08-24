package com.example.bioregistro.data.repository

import com.example.bioregistro.data.model.BirdObservation
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query

class ObservationRepository {

    private val firestore =
        FirebaseFirestore.getInstance()

    private val observationsCollection =
        firestore.collection("observations")

    /*
     * GUARDAR AVISTAMIENTO
     */
    fun saveObservation(
        observation: BirdObservation,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {

        val data = hashMapOf<String, Any>(
            "species" to observation.species,
            "quantity" to observation.quantity,
            "location" to observation.location,
            "latitude" to observation.latitude,
            "longitude" to observation.longitude,
            "date" to observation.date,
            "observations" to observation.observations,
            "imageBase64" to observation.imageBase64,
            "createdAt" to FieldValue.serverTimestamp()
        )

        observationsCollection
            .add(data)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onError(exception)
            }
    }

    /*
     * ELIMINAR AVISTAMIENTO
     */
    fun deleteObservation(
        observationId: String,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {

        if (observationId.isBlank()) {

            onError(
                IllegalArgumentException(
                    "El identificador del avistamiento no es válido."
                )
            )

            return
        }

        observationsCollection
            .document(observationId)
            .delete()
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onError(exception)
            }
    }

    /*
     * ESCUCHAR AVISTAMIENTOS
     */
    fun listenObservations(
        onChange: (List<BirdObservation>) -> Unit,
        onError: (Exception) -> Unit
    ): ListenerRegistration {

        return observationsCollection
            .orderBy(
                "createdAt",
                Query.Direction.DESCENDING
            )
            .addSnapshotListener { snapshot, exception ->

                if (exception != null) {

                    onError(exception)

                    return@addSnapshotListener
                }

                if (snapshot == null) {

                    onChange(emptyList())

                    return@addSnapshotListener
                }

                val observationList =
                    snapshot.documents.map { document ->

                        BirdObservation(
                            id = document.id,

                            species =
                                document.getString("species")
                                    ?: "",

                            quantity =
                                document.getLong("quantity")
                                    ?.toInt()
                                    ?: 0,

                            location =
                                document.getString("location")
                                    ?: "",

                            latitude =
                                document.getDouble("latitude")
                                    ?: 0.0,

                            longitude =
                                document.getDouble("longitude")
                                    ?: 0.0,

                            date =
                                document.getString("date")
                                    ?: "",

                            observations =
                                document.getString("observations")
                                    ?: "",

                            imageBase64 =
                                document.getString("imageBase64")
                                    ?: ""
                        )
                    }

                onChange(observationList)
            }
    }
}