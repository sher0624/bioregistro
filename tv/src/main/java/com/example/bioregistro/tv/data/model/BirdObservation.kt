package com.example.bioregistro.tv.data.model

data class BirdObservation(
    val id: String = "",
    val species: String = "",
    val quantity: Int = 0,
    val location: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val date: String = "",
    val observations: String = "",
    val imageBase64: String = ""
)