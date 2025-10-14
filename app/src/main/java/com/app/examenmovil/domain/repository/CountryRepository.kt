package com.app.examenmovil.domain.repository

import com.app.examenmovil.domain.model.Country

interface CountryRepository {
    //Result T representa un exito o error
    //List<Country> es el tipo de dato que esperamos en caso de exito
    suspend fun getCountriesByName(name: String): Result<List<Country>>
}