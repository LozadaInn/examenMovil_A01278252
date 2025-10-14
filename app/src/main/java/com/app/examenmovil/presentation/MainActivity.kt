package com.app.examenmovil.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels // Importación para el by viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.examenmovil.domain.model.Country
import com.app.examenmovil.presentation.theme.ExamenMovilTheme
import com.tuspaquetes.AppModule

class MainActivity : ComponentActivity() {

    // 1. INICIALIZAR EL VIEWMODEL
    // Usamos 'by viewModels' con nuestra Factory para crear una instancia del ViewModel.
    private val viewModel: CountryViewModel by viewModels {
        // Pasa los dos UseCases desde AppModule
        CountryViewModelFactory(
            AppModule.getAllCountriesUseCase, // <--- AÑADIR
            AppModule.getCountryByNameUseCase
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExamenMovilTheme {
                // Llamamos a nuestra pantalla principal de Compose
                CountrySearchScreen(viewModel)
            }
        }
    }
}

// 2. CREAR LA PANTALLA PRINCIPAL (COMPOSABLE)
@Composable
fun CountrySearchScreen(viewModel: CountryViewModel) {
    // la IA sugirió agregar observadores para los datos del ViewModel
    // Cada vez que estos datos cambien, la UI se redibujará automáticamente
    // la implementación de observeAsState la realicé completamente con IA
    val countries by viewModel.countries.observeAsState(initial = emptyList())
    val isLoading by viewModel.isLoading.observeAsState(initial = false)
    val error by viewModel.error.observeAsState(initial = null)
    var searchText by remember { mutableStateOf("") }

    // --- Diseño de la UI HECHO EN SU MAYORÍA CON IA---
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Busqueda
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    label = { Text("Nombre del país") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = { viewModel.searchCountry(searchText) },
                    enabled = !isLoading // El botón se deshabilita mientras está cargando
                ) {
                    Text("Buscar")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // --- Contenido Principal (Carga, Error o Lista) ---
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                if (isLoading) {
                    // Si está cargando, muestra un CircularProgressIndicator
                    CircularProgressIndicator()
                } else if (error != null) {
                    // Si hay un error, muéstralo
                    Text(text = error!!, color = MaterialTheme.colorScheme.error)
                } else {
                    // Si no hay carga ni error, muestra la lista de países
                    CountryList(countries = countries)
                }
            }
        }
    }
}

// 3. COMPOSABLE PARA LA LISTA DE PAISES
@Composable
fun CountryList(countries: List<Country>) {
    if (countries.isEmpty()) {
        Text(text = "No se encontraron países. Realiza una búsqueda.")
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(countries) { country ->
                CountryItem(country = country)
            }
        }
    }
}

// 4. COMPOSABLE PARA UN ÚNICO ITEM DE LA LISTA
@Composable
fun CountryItem(country: Country) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = country.nombreComun, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Oficial: ${country.nombreOficial}")
            Text(text = "Capital: ${country.capital}")
            Text(text = "Población: ${country.poblacion}")
            Text(text = "Región: ${country.region}")
            // Implementar lo de mostrar bandera
            Text(text = "Bandera: ${country.urlBandera}")
        }
    }
}