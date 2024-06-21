<h1 align="center">Android-Compose-MVVM</h1>

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

## Package Structures

```
com.hivian.compose_mvvm                     # Root Module
├── App                                     # Application entry point
└── MainActivity                            # Screen entry point

com.hivian.compose_mvvm.basic-feature       # Main feature Module
├── data                                    # Data layer
│   ├── di                                  # Dependency injection module
│   ├── mappers                             # DTO to domain models mapper
│   ├── repository                          # Interact with local & remote data source
│   └── sources                             # Data source services implementations
├── domain                                  # Domain layer
│   ├── di                                  # Dependency injection module
│   ├── models                              # Domain models
│   ├── repository                          # Repository contract
│   ├── services                            # Data source services contracts
│   └── usecases                            # Use cases encapsulation for presentation layer
└── presentation                            # Presentation layer
    ├── di                                  # Dependency injection module
    ├── extensions                          # Platform-specific Kotlin extensions
    ├── home                                # Main screen & viewModel
    ├── detail                              # Detail screen & viewModel
    └── themes                              # Design system

com.hivian.compose_mvvm.core                # Core Module
├── di                                      # Dependency injection module
├── base                                    # Base classes
├── datasources                             # Data sources
│   ├── models                              # Entities & DTO Models
│   ├── local                               # Local database client
│   │   ├── converters                      # Complex data serializer
│   │   └── dao                             # Data Access Object for Room
│   └── remote                              # Remote data client & data wrappers
├── extensions                              # Kotlin extensions
└── services                                # Core services
```


## Download

Go to the [releases page](https://github.com/hivian/Android-Compose-MVVM/releases) to download the latest available apk.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details
