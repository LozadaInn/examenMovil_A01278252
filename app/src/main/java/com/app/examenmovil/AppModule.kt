package com.tuspaquetes

import com.app.examenmovil.data.remote.ApiService
import com.app.examenmovil.data.repository.CountryRepositoryImpl
import com.app.examenmovil.domain.repository.CountryRepository
import com.app.examenmovil.domain.use_case.GetAllCountriesUseCase
import com.app.examenmovil.domain.use_case.GetCountryByNameUseCase
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
// Esta importación me la sugirió el IDE para el convertidor
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

object AppModule {

    private const val BASE_URL = "https://restcountries.com/v3.1/"

    private val json = Json {
        ignoreUnknownKeys = true
    }

    val apiService: ApiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        // Aquí se utiliza la función de extensión para crear el convertidor
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()
        .create(ApiService::class.java)

    // instacia del repositorio
    private val countryRepository: CountryRepository = CountryRepositoryImpl(apiService)
    // isntancia del caso de uso
    val getCountryByNameUseCase: GetCountryByNameUseCase = GetCountryByNameUseCase(countryRepository)

    val getAllCountriesUseCase: GetAllCountriesUseCase = GetAllCountriesUseCase(countryRepository)
}
