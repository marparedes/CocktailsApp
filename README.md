# Cocktails App

Cocktails App muestra una lista de cocktails con sus respectivos datos como: nombre de la bebida, ingredientes y si es una bebida alcoholica o no. Tambien se podrá realizar una busqueda por el nombre del cocktail.

## Features
- Buscar cocktails por el nombre.
- Ver informacion sobre el cocktail en cards (nombre, ingredientes, si contiene alcohol o no mediante un icono).
- Se obtienen los datos de una API llamada [“TheCocktailDB”](https://www.thecocktaildb.com/api.php).

## Arquitectura
Este proyecto sigue el patrón de arquitectura MVVM y Clean Architecture.

### MVVM (Model-View-ViewModel)

- **Model**: Maneja la lógica de negocio y de acceso a datos.
- **View**: Maneja la lógica de la interfaz de usuario. En este proyecto sería la MainActivity.
- **ViewModel**: Maneja la data relacionada con la UI. Proporciona datos a la View y reacciona a las interacciones del usuario.

### Clean Architecture

- **Data Layer**: Maneja las operaciones de datos. Contiene repositories y la fuenta de datos remota (API).
- **Domain Layer**: Contiene la lógica de negocio y los casos de uso.
- **Presentation Layer**: Contiene la lógica de presentación y la interacción con la UI. En este proyecto se representa como UI.


## Tecnologías y librerías utilizadas
- **Kotlin**: Lenguaje de programación principal.
- **Hilt**: Para la inyección de dependencias.
- **Retrofit**: Para las llamadas a la API.
- **Coroutines**: Para manejar tareas asíncronas.
- **Flow**: Para la programación reactiva.
- **MockK**: Para las pruebas de unidad.
- **Turbine**: Para las pruebas con kotlinx.coroutines Flow
- **JUnit**: Framework de pruebas.


## Prerrequisitos

- Android Studio Arctic Fox o superior.
- JDK 8 o superior.
