package com.qdroid.anime.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.retain.retain
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.qdroid.anime.ui.details.AnimeDetailsScreen
import com.qdroid.anime.ui.home.HomeScreen
import kotlinx.serialization.Serializable

@Serializable

sealed interface ScreenRoute : NavKey {
    @Serializable
    data object HomeRoute : ScreenRoute

    @Serializable
    data class AnimeDetailsRoute(val id: Int) : ScreenRoute

}

@Composable
fun MainNavigation() {

    val backStack = retain { mutableStateListOf<Any>(ScreenRoute.HomeRoute) }

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<ScreenRoute.HomeRoute> {
                HomeScreen(onNavigateToDetails = { id ->
                    backStack.add(
                        ScreenRoute.AnimeDetailsRoute(id)
                    )
                })
            }
            entry<ScreenRoute.AnimeDetailsRoute> { key ->
                AnimeDetailsScreen(id = key.id, onNavigateBack = { backStack.removeLastOrNull() })
            }
        }
    )
}