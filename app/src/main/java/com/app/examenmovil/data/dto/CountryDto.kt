package com.app.examenmovil.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CountryDto(
    val name: NameDto,
    val flags: FlagsDto,
    val capital: List<String>? = null,
    val population: Long,
    val region: String
)
    