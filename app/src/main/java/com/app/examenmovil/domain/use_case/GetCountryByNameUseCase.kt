package com.app.examenmovil.domain.use_case

import com.app.examenmovil.domain.model.Country
import com.app.examenmovil.domain.repository.CountryRepository

class GetCountryByNameUseCase(
    private val repository: CountryRepository
) {
    // Esta parte fue generada casi completamente por chatGPT, invoke sirve para llamar a la clase como si fuera una función
    // Agregué la validación para evitar llamadas innecesarias a la API cuando el nombre
    // está vacío.
    suspend operator fun invoke(name: String): Result<List<Country>> {
        if (name.isBlank()) {
            return Result.success(emptyList()) // Devolvemos una lista vacía si la búsqueda está en blanco.
        }
        return repository.getCountriesByName(name)
    }
}
