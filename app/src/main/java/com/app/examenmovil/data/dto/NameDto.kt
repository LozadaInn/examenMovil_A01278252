package com.app.examenmovil.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class NameDto(
    val common: String,
    val official: String
)
