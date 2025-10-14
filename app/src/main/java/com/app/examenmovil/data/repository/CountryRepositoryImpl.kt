package com.app.examenmovil.data.repository

import com.app.examenmovil.data.remote.ApiService
import com.app.examenmovil.data.remote.dto.toDomain
import com.app.examenmovil.domain.model.Country
import com.app.examenmovil.domain.repository.CountryRepository

class CountryRepositoryImpl(
    private val apiService: ApiService
) : CountryRepository {

    override suspend fun getCountriesByName(name: String): Result<List<Country>> {
        return try {
            val countryDtos = apiService.getCountryByName(name)
            Result.success(
                countryDtos.map { it.toDomain() }
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAllCountries(): Result<List<Country>> {
        return try {
            val requiredFields = "name,flags,capital,population,region"
            val countryDtos = apiService.getAllCountries(requiredFields)
            Result.success(
                countryDtos.map { it.toDomain() }
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
