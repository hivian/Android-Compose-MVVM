<h1 align="center">RandomUsersComposeAndroid</h1>

<p align="center">
  <a href="https://developer.android.com/jetpack/compose">
    <img src="https://img.shields.io/badge/compose-1.6.8-brightgreen" alt="Compose Version">
  </a>
  <a href="https://kotlinlang.org/docs/whatsnew18.html">
    <img src="https://img.shields.io/badge/kotlin-2.0.0-blue" alt="Kotlin Version">
  </a>
  <a href="https://docs.gradle.org/8.0.2/release-notes.html">
    <img src="https://img.shields.io/badge/gradle-8.4.1-blue" alt="Gradle">
  </a>
  <a href="https://android-arsenal.com/api?level=27">
    <img src="https://img.shields.io/badge/API-27%2B-blue" alt="API">
  </a>
  <a href="https://github.com/hivian/Android-Compose-MVVM/blob/master/LICENSE">
    <img src="https://img.shields.io/badge/License-MIT-green" alt="License">
  </a>
</p>

<p align="center">
  A simple demo app built with Kotlin, using Jetpack Compose, based on clean architecture and MVVM pattern. <br/>
  Data fetched from https://randomuser.me api and saved to Android database
</p>

## Preview
<p>
  <img src="preview1.gif" width="270"/>
  <img src="preview2.gif" width="270"/>
</p>

## Features

- [x] Offline mode
- [x] Pagination: infinite scroll
- [x] Reverse geocoding with Maps SDK
- [x] Specific error messages with retry action
- [x] Dark mode

## Tech stack

* [Compose](https://developer.android.com/jetpack/compose) - Declarative and simplified way for UI development
* [Maps Compose](https://developers.google.com/maps/documentation/android-sdk/maps-compose) - Compose for the Goggle Maps SDK
* [Koin](https://insert-koin.io/docs/quickstart/android/) - Dependency injection
* [Navigation](https://developer.android.com/topic/libraries/architecture/navigation) - Screen routing handler
* [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - UI related data holder, lifecycle aware
* [Room](https://developer.android.com/topic/libraries/architecture/room) - Local database
* [Retrofit](https://square.github.io/retrofit/) - Networking client
* [Coroutines](https://developer.android.com/topic/libraries/architecture/coroutines) - Concurrency design pattern for asynchronous programming

## Architecture

* SOLID principles
* MVVM clean architecture
* Modularization by feature and by layer
  
### Modules dependencies
```
App → homefeature
    ↳ core   
```

### Layers dependencies
```
presentation → domain ← data
```
  
### Global package structure
```
com.hivian.randomusers                      # Root Module
├── app                                     # Application entry point
└── MainActivity                            # Screen entry point
                                                   
com.hivian.randomusers.homefeature          # Main feature Module
├── di                                      # Dependency injection module
├── data                                    # Data layer
│   ├── mappers                             # DTO to domain models mapper
│   └── services                            # local & remote data source implementation
├── domain                                  # Domain layer
│   ├── models                              # Domain models
│   ├── services                            # services contracts
│   └── usecases                            # Use cases encapsulation for presentation layer
└── presentation                            # Presentation layer
    ├── home                                # Main screen & viewModel
    ├── detail                              # Detail screen & viewModel
    └── themes                              # Design system

com.hivian.randomusers.core                 # Core Module
├── di                                      # Dependency injection module
├── data                                    # Data sources
│   ├── models                              # Entities & DTO Models
│   ├── local                               # Local database client
│   │   ├── converters                      # Complex data serializer
│   │   └── dao                             # Data Access Object for Room
│   └── remote                              # Remote data client & data wrappers
├── domain                                  # Domain layer
│   ├── base                                # Base classes
│   ├── extensions                          # Kotlin extensions
│   ├── services                            # Core services contracts
│   └── usecases                            # Core usecases
└── presentation                            # Presentation layer
    ├── navigation                          # Navigation routes
    └── services                            # UI services implementation
```


## Download

Go to the [releases page](https://github.com/hivian/Android-Compose-MVVM/releases) to download the latest available apk.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details
