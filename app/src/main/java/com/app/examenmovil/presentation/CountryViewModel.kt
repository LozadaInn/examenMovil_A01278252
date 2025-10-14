package com.app.examenmovil.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.examenmovil.domain.model.Country
import com.app.examenmovil.domain.use_case.GetCountryByNameUseCase
import kotlinx.coroutines.launch

// El ViewModel recibe el caso de uso como dependencia
class CountryViewModel(
    private val getCountryByNameUseCase: GetCountryByNameUseCase
) : ViewModel() {
    private val _countries = MutableLiveData<List<Country>>()
    val countries: LiveData<List<Country>> = _countries

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun searchCountry(name: String) {
        viewModelScope.launch {
            _isLoading.value = true // Inicia la carga

            val result = getCountryByNameUseCase(name)

            result.onSuccess { countryList ->
                _countries.value = countryList
                _error.value = null
            }.onFailure { throwable ->
                _countries.value = emptyList() // Limpiamos la lista
                _error.value = "Error: ${throwable.message}"
            }

            _isLoading.value = false // Finaliza la carga
        }
    }
}
