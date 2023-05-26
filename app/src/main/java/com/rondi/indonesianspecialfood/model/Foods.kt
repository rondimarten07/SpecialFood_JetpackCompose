package com.rondi.indonesianspecialfood.model

data class Foods(
    val id: Int,
    val name: String,
    val description: String,
    val comeFrom: String,
    val photoUrl: String,
    val rating: Double,
    var isFavorite: Boolean = false,
)