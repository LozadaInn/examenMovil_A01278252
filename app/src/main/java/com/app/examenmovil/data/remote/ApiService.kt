package com.app.examenmovil.data.remote

import android.R.attr.name
import com.app.examenmovil.data.dto.CountryDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("all")
    suspend fun getAllCountries(): List<CountryDto>

    @GET("name/{name}")
    suspend fun getCountryByName(@Path("name") name: String): List<CountryDto>
}