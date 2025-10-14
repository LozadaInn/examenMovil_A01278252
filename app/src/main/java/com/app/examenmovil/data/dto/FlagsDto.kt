package com.app.examenmovil.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class FlagsDto(
    val png: String,
    val svg: String? = null
)
