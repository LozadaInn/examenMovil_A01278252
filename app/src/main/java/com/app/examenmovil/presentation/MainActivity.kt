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
                // Estado para gestionar la navegación simple: `null` para la lista, un objeto `Country` para el detalle.
                var selectedCountry by remember { mutableStateOf<Country?>(null) }

                // Lógica que decide qué pantalla mostrar
                if (selectedCountry == null) {
                    // Si no hay país seleccionado, muestra la pantalla principal de búsqueda y lista.
                    CountryListScreen(
                        viewModel = viewModel,
                        // Cuando se hace clic en un país, actualizamos el estado para navegar al detalle.
                        onCountryClicked = { country ->
                            selectedCountry = country
                        }
                    )
                } else {
                    // Si se ha seleccionado un país, muestra la pantalla de detalle.
                    CountryDetailScreen(
                        country = selectedCountry!!, // Usamos '!!' porque sabemos que no es null en este bloque.
                        // Para volver, simplemente reseteamos el estado a `null`.
                        onBackClicked = {
                            selectedCountry = null
                        }
                    )
                }
            }
        }
    }
}


/**
 * Composable principal que contiene la barra de búsqueda y el contenedor del contenido (lista, carga o error).
 */
@Composable
fun CountryListScreen(
    viewModel: CountryViewModel,
    onCountryClicked: (Country) -> Unit
) {
    // Observamos los estados del ViewModel. Compose se redibujará cuando cambien.
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
            // Barra de búsqueda
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

            // Contenedor principal que muestra Carga, Error o la Lista de países.
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                when {
                    isLoading -> CircularProgressIndicator()
                    error != null -> ErrorState(
                        errorMessage = error!!,
                        onRetry = { viewModel.loadAllCountries() }
                    )
                    else -> CountryListContent(
                        countries = countries,
                        onCountryClicked = onCountryClicked
                    )
                }
            }
        }
    }
}

/**
 * Composable que muestra el mensaje de error y un botón para reintentar la acción.
 */
@Composable
fun ErrorState(errorMessage: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = errorMessage,
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Text("Reintentar")
        }
    }
}

/**
 * Composable que muestra la LazyColumn con la lista de países o un mensaje si la lista está vacía.
 */
@Composable
fun CountryListContent(countries: List<Country>, onCountryClicked: (Country) -> Unit) {
    if (countries.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "No se encontraron países.")
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(countries) { country ->
                CountryItem(country = country, onCountryClicked = onCountryClicked)
            }
        }
    }
}

/**
 * Composable para un único ítem de la lista (una tarjeta de país). Es clickeable.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryItem(country: Country, onCountryClicked: (Country) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        onClick = { onCountryClicked(country) }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = country.nombreComun, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Oficial: ${country.nombreOficial}")
            Text(text = "Capital: ${country.capital}")
            Text(text = "Población: ${country.poblacion}")
            Text(text = "Región: ${country.region}")
        }
    }
}

/**
 * Composable para la pantalla de detalle del país. Muestra la bandera y más información.
 */
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
            // Muestra la imagen de la bandera usando Coil
            AsyncImage(
                model = country.urlBandera,
                contentDescription = "Bandera de ${country.nombreComun}",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
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
                Text(text = "Capital: ${country.capital}", style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Población: ${country.poblacion}", style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Región: ${country.region}", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}
