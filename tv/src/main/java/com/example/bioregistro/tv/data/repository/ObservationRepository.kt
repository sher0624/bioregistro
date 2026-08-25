package com.example.bioregistro.tv.data.repository

import com.example.bioregistro.tv.data.model.BirdObservation
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query

class ObservationRepository {

    private val firestore = FirebaseFirestore.getInstance()

    private val observationsCollection =
        firestore.collection("observations")

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

                val observations = snapshot.documents.map { document ->

                    BirdObservation(
                        id = document.id,
                        species =
                            document.getString("species") ?: "",
                        quantity =
                            document.getLong("quantity")
                                ?.toInt() ?: 0,
                        location =
                            document.getString("location") ?: "",
                        latitude =
                            document.getDouble("latitude") ?: 0.0,
                        longitude =
                            document.getDouble("longitude") ?: 0.0,
                        date =
                            document.getString("date") ?: "",
                        observations =
                            document.getString("observations") ?: "",
                        imageBase64 =
                            document.getString("imageBase64") ?: ""
                    )
                }

                onChange(observations)
            }
    }
}