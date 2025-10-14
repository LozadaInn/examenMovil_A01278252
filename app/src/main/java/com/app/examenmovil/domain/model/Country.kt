package com.app.examenmovil.domain.model

data class Country(
    val nombreComun: String,
    val nombreOficial: String,
    val capital: String,
    val poblacion: Long,
    val region: String,
    val urlBandera: String
)
