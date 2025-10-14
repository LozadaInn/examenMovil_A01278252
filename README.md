## Iñaki Salvador Pérez Lozada - A01278252
### Android
---
### ¿Qué realicé y que faltó?


### Requerimientos Funcionales

| Funcionalidad             | Estado            | Comentarios                                                                                                                                                                 |
| :------------------------ | :---------------: | :-------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Listado de Países**     |  **Completado** | La aplicación obtiene la lista completa de países desde la API al iniciar. Todos los países se pueden clickear.                                                          |
| **Detalle del País**      |  **Completado** | Al hacer clic en un país, se muestran los detalles del mismo junto con su bandera.                                                |
| **Manejo de Estados**     |  **Completado** | Se implementaron estados visuales para `Cargando` (un indicador de progreso), `Éxito` (la lista o detalle) y `Error`.                                                          |
| **Reintento en Errores**  |  **Completado** | Cuando falla la carga inicial de datos (ej. por falta de conexión), se muestra un mensaje de error claro y un botón de "Reintentar".                                          |
| **Persistencia**          |  **No completado** | No implementé el uso de preferencias del usuario, busqué la manera de implementarlo pero no funcionó y decidí borrarlo |
| **Búsqueda Simple**       |  **Completado** | Se implementó una barra de búsqueda funcional. Al buscar, se filtra la lista de países según el nombre ingresado.                                                            |
| **Self-explained Hook**   |  **Completado** | Hay un botón de información en la pantalla principal que despliega uuna explicación de la arquitectura y estrategias utilizadas en la app.                    |

### Requerimientos No Funcionales

| Requerimiento               | Estado            | Comentarios                                                                                                                                                                   |
| :-------------------------- | :---------------: | :---------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Arquitectura MVVM**       |  **Completado** | La aplicación sigue el patrón MVVM, donde el `ViewModel` expone estados a la UI (`View`) y gestiona la lógica de negocio.                                                        |
| **Clean Architecture**      |  **Completado** | El proyecto está estructurado en capas `presentation`, `domain` y `data`, separando responsabilidades y desacoplando la lógica de la UI y las fuentes de datos.                 |
| **UI Moderna**              |  **Completado** | La interfaz de usuario fue construida en su totalidad con **Jetpack Compose**.                                                                                                |
| **Diseño y UX**             |  **Completado** | El diseño es claro y responsivo. Se manejan estados de carga y error para una buena experiencia de usuario.                                                                    |
| **Corrutinas (No Bloqueo)** |  **Completado** | Todas las operaciones de red (API) y de disco (`SharedPreferences`) se ejecutan en segundo plano utilizando Corrutinas de Kotlin (`viewModelScope`), garantizando que la UI nunca se bloquee. |
| **Inyección de Dependencias con Hilt** |  **No Completado** | No implementé dependencias con Hilt |
| **Repositorio GitHub**      |  **Completado** | El proyecto se encuentra en un repositorio público individual con este README.                                                                                                |

---

## Uso de Asistencia por Inteligencia Artificial

Durante el desarrollo de este proyecto, usé Inteligencia Artificial generativa (en este caso, un asistente tipo Gemini) como herramienta de apoyo. Estas son las áreas en las que más la usé.

### Situaciones de Mayor Uso de la IA:

1.  **Generación de Boilerplate y Código Repetitivo:** La IA fue fundamental para generar rápidamente el código estructural de los componentes de Jetpack Compose (ej. `Cards`, `Columns`, `Scaffolds`), la configuración inicial de Retrofit y la estructura en general de la UI.

2.  **Implementación de Nuevos Conceptos:** Para funcionalidades que requerían configuración específica, como la librería **Coil** u Okhttp que son dependencias que usé en algunos tutoriales de YT, la IA proporcionó ejemplos de código funcionales que luego adapté y a la app.

3.  **Resolución de Errores y Depuración (`Debugging`):** Cuando me encontraba con errores de compilación o de ejecución (como el error de `deprecation warning` de `MediaType`), pegar el error en la IA me proporcionaba un análisis rápido de la causa raíz y la solución más común, acelerando significativamente el proceso de depuración.

4.  **Mejora de Código:** Utilicé la IA para obtener sugerencias sobre cómo mejorar el código existente.

### Comentarios adicionales:

Creo que pude mejorar muchas cosas en este examen, sobretodo en la UI de la aplicación, pues todo está hecho en la MainActivity y no separado como en los laboratorios de Pokedex, decidí proceder de esta manera principalmente por el tiempo de la evaluación.
