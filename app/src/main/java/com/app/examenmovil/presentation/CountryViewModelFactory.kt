package com.app.examenmovil.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.examenmovil.domain.use_case.GetCountryByNameUseCase

class CountryViewModelFactory(
    private val useCase: GetCountryByNameUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CountryViewModel::class.java)) {
            // Si el sistema pide un CountryViewModel, creamos uno con el caso de uso que recibimos.
            @Suppress("UNCHECKED_CAST")
            return CountryViewModel(useCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
