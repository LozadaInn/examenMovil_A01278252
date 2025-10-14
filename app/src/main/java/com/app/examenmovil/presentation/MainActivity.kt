package com.app.examenmovil.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.app.examenmovil.presentation.theme.ExamenMovilTheme
import com.tuspaquetes.AppModule
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // INICIO DE LA PRUEBA DE LA API

        // Archivo: MainActivity.kt

        lifecycleScope.launch {
            try {
                val api = AppModule.apiService

                // ASEGÚRATE DE QUE ESTÁS USANDO ESTA LÍNEA:
                val countries = api.getCountryByName("Canada")

                // Si la llamada fue exitosa, imprimimos los resultados en el Logcat.
                Log.d("APITest", "¡Llamada exitosa! Países encontrados: ${countries.size}")
                Log.d("APITest", "Primer resultado: ${countries.firstOrNull()}")

            } catch (e: Exception) {
                // Si algo salió mal, imprimimos el error en el Logcat.
                Log.e("APITest", "Error en la llamada a la API", e)
            }
        }


        // --- FIN DE LA PRUEBA DE API ---


        // El resto es solo para tener algo que mostrar en pantalla
        setContent {
            ExamenMovilTheme { // <-- Asegúrate de que este sea el nombre correcto de tu tema
                Box(
                    modifier = Modifier.Companion.fillMaxSize(),
                    contentAlignment = Alignment.Companion.Center
                ) {
                    Text(text = "Probando la API... Revisa el Logcat.")
                }
            }
        }
    }
}