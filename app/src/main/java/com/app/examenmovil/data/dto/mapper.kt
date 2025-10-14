package com.app.examenmovil.data.remote.dto

import com.app.examenmovil.data.dto.CountryDto
import com.app.examenmovil.domain.model.Country

// Convierte de la API a la app
fun CountryDto.toDomain(): Country {
    return Country(
        nombreComun = name.common,
        nombreOficial = name.official,
        // Si no hay capital ponemos "N/A"
        capital = capital?.firstOrNull() ?: "N/A",
        poblacion = population,
        region = region,
        urlBandera = flags.png
    )
}
