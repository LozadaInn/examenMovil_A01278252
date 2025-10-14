package com.app.examenmovil.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.examenmovil.domain.use_case.GetAllCountriesUseCase
import com.app.examenmovil.domain.use_case.GetCountryByNameUseCase

class CountryViewModelFactory(
    private val getAllCountriesUseCase: GetAllCountriesUseCase,
    private val getCountryByNameUseCase: GetCountryByNameUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CountryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            // 2. PASA AMBOS USE CASES
            return CountryViewModel(getAllCountriesUseCase, getCountryByNameUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

