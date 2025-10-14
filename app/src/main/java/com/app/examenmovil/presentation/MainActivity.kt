package com.app.examenmovil.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.app.examenmovil.domain.model.Country
import com.app.examenmovil.presentation.theme.ExamenMovilTheme // --> CAMBIO: Corregí el import, asegúrate que sea el tuyo.
import com.tuspaquetes.AppModule

class MainActivity : ComponentActivity() {

    private val viewModel: CountryViewModel by viewModels {
        CountryViewModelFactory(
            AppModule.getAllCountriesUseCase,
            AppModule.getCountryByNameUseCase
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExamenMovilTheme {
                // --> CAMBIO: Creamos un estado para saber qué país está seleccionado.
                // Si es `null`, mostramos la lista. Si no, mostramos los detalles.
                var selectedCountry by remember { mutableStateOf<Country?>(null) }

                // --> CAMBIO: Lógica para decidir qué pantalla mostrar.
                if (selectedCountry == null) {
                    // Si no hay país seleccionado, muestra la pantalla de búsqueda y lista.
                    CountrySearchScreen(
                        viewModel = viewModel,
                        // Al hacer clic, actualizamos el estado con el país seleccionado.
                        onCountryClicked = { country ->
                            selectedCountry = country
                        }
                    )
                } else {
                    // Si hay un país seleccionado, muestra la pantalla de detalle.
                    CountryDetailScreen(
                        country = selectedCountry!!,
                        // Para volver, simplemente ponemos el estado a null.
                        onBackClicked = {
                            selectedCountry = null
                        }
                    )
                }
            }
        }
    }
}

// --> CAMBIO: La función ahora acepta una lambda `onCountryClicked`.
@Composable
fun CountrySearchScreen(
    viewModel: CountryViewModel,
    onCountryClicked: (Country) -> Unit
) {
    val countries by viewModel.countries.observeAsState(initial = emptyList())
    val isLoading by viewModel.isLoading.observeAsState(initial = false)
    val error by viewModel.error.observeAsState(initial = null)
    var searchText by remember { mutableStateOf("") }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Barra de búsqueda (sin cambios)
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    label = { Text("Nombre del país") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { viewModel.searchCountry(searchText) }, enabled = !isLoading) {
                    Text("Buscar")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Contenido Principal
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                if (isLoading) {
                    CircularProgressIndicator()
                } else if (error != null) {
                    Text(text = error!!, color = MaterialTheme.colorScheme.error)
                } else {
                    // --> CAMBIO: Pasamos la función `onCountryClicked` a la lista.
                    CountryList(countries = countries, onCountryClicked = onCountryClicked)
                }
            }
        }
    }
}

// --> CAMBIO: La lista ahora acepta y pasa la función `onCountryClicked`.
@Composable
fun CountryList(countries: List<Country>, onCountryClicked: (Country) -> Unit) {
    if (countries.isEmpty()) {
        Text(text = "No se encontraron países. Realiza una búsqueda.")
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(countries) { country ->
                // --> CAMBIO: Pasamos la función `onCountryClicked` a cada item.
                CountryItem(country = country, onCountryClicked = onCountryClicked)
            }
        }
    }
}

// CountryItem ahora es clickeable.
@OptIn(ExperimentalMaterial3Api::class) //Implementado con IA
@Composable
fun CountryItem(country: Country, onCountryClicked: (Country) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        // Al hacer clic en la tarjeta, llamamos a la función con el país actual.
        onClick = { onCountryClicked(country) }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = country.nombreComun, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Oficial: ${country.nombreOficial}")
            Text(text = "Capital: ${country.capital}")
            Text(text = "Población: ${country.poblacion}")
            Text(text = "Región: ${country.region}")
            Text(text = "Bandera: ${country.urlBandera}")
        }
    }
}

// Composable para la pantalla de detalle del país
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryDetailScreen(country: Country, onBackClicked: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = country.nombreComun) },
                navigationIcon = {
                    IconButton(onClick = onBackClicked) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // --> CAMBIO: Usamos AsyncImage de Coil para mostrar la bandera
            AsyncImage(
                model = country.urlBandera, // La URL de la imagen
                contentDescription = "Bandera de ${country.nombreComun}",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp), // Damos una altura fija a la bandera
                contentScale = ContentScale.Crop // Escala la imagen para que llene el espacio
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = country.nombreOficial,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Capital: ${country.capital}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Población: ${country.poblacion}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Región: ${country.region}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}
