package com.app.examenmovil.domain.use_case

import com.app.examenmovil.domain.model.Country
import com.app.examenmovil.domain.repository.CountryRepository

class GetAllCountriesUseCase(
    private val repository: CountryRepository
) {
    suspend operator fun invoke(): Result<List<Country>> {
        return repository.getAllCountries()
    }
}
