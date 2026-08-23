package com.example.bioregistro.model

data class BirdObservation(
    val id: String = "",
    val species: String = "",
    val quantity: Int = 0,
    val location: String = "",
    val date: String = "",
    val observations: String = "",
    val imageUri: String = "",
    val temperature: Double = 0.0,
    val weather: String = ""
)