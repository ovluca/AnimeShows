### Project Solution Architecture

This document explains the architectural choices and tools used for the AnimeShows project.

### Architecture Overview
The project follows **Clean Architecture** principles, divided into three distinct layers to ensure separation of concerns, scalability, and testability.

*   **Data Layer**: Responsible for data retrieval and mapping. It contains the `AnimeRepositoryImpl`, which interacts with the Apollo GraphQL client to fetch data from the AniList API.
*   **Domain Layer**: Contains the business logic of the application. It defines the `AnimeRepository` interface, data models (e.g., `AnimeMovie`, `AnimeMovieDetails`), and Use Cases (e.g., `TrendingNowUseCase`, `PopularNowUseCase`, `GetAnimeMovieDetailsUseCase`). This layer is independent of any platform-specific frameworks.
*   **Presentation Layer**: Handles the UI and user interactions. It is built using **Jetpack Compose** and follows the **MVI (Model-View-Intent)** design pattern.

### Design Pattern: MVI (Model-View-Intent)
The presentation layer implements MVI to manage state in a predictable way:
*   **Model (UiState)**: Represents the current state of the screen (e.g., `HomeUiState`, `AnimeDetailsUiState`).
*   **View**: Composable functions that observe the `UiState` and render the UI.
*   **Intent**: User actions or system events that trigger a state change (e.g., `HomeScreenIntent`, `AnimeDetailsIntent`).
*   **Events**: One-time effects like navigation or showing snackbars are handled via a `SharedFlow` (e.g., `HomeEvents`, `AnimeDetailsEvents`).

### Key Technologies & Libraries
*   **Koin**: Used as the Dependency Injection framework. It is lightweight and provides a DSL for easy configuration of modules (`AppModule`, `RepositoryModule`, `UseCaseModule`, `ViewModelModule`).
*   **Apollo GraphQL (graphQA)**: Used for efficient data fetching. It allows the app to request only the specific fields needed, reducing network payload.
*   **Navigation3 (Compose Navigation3)**: The latest iteration of Compose Navigation, offering a more declarative and type-safe way to handle transitions between screens (Home and Details).
*   **Coil**: An image loading library for Android backed by Kotlin Coroutines.
*   **Kotlinx Serialization**: Used for type-safe navigation and data parsing.

### Data Flow
1.  The **View** sends an **Intent** to the **ViewModel**.
2.  The **ViewModel** invokes the relevant **UseCase**.
3.  The **UseCase** requests data from the **Repository**.
4.  The **Repository** fetches data using the **Apollo Client** and maps it to domain models.
5.  The **ViewModel** updates the **UiState**.
6.  The **View** reacts to the updated **UiState** and re-renders.
