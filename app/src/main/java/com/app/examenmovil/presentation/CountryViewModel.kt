package com.app.examenmovil.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.examenmovil.domain.model.Country
import com.app.examenmovil.domain.use_case.GetAllCountriesUseCase
import com.app.examenmovil.domain.use_case.GetCountryByNameUseCase
import kotlinx.coroutines.launch

// El ViewModel recibe el caso de uso como dependencia
class CountryViewModel(
    private val getAllCountriesUseCase: GetAllCountriesUseCase,
    private val getCountryByNameUseCase: GetCountryByNameUseCase
) : ViewModel() {
    private val _countries = MutableLiveData<List<Country>>()
    val countries: LiveData<List<Country>> = _countries

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    //Funcion nueva para cargar todos los paises al iniciar
    init {
        loadAllCountries()
    }

    fun loadAllCountries() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            val result = getAllCountriesUseCase()
            result.onSuccess { countryList ->
                _countries.value = countryList
            }.onFailure { throwable ->
                _countries.value = emptyList()
                _error.value = "Error al cargar la lista de países: ${throwable.message}"
            }
            _isLoading.value = false
        }
    }
    fun searchCountry(name: String) {
        if (name.isBlank()) {
            loadAllCountries()
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            val result = getCountryByNameUseCase(name)

            result.onSuccess { countryList ->
                _countries.value = countryList
            }.onFailure { throwable ->
                _countries.value = emptyList()
            }

            _isLoading.value = false
        }
    }
}
