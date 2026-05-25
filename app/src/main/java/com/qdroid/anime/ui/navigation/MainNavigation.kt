package com.qdroid.anime.ui.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.ui.NavDisplay
import com.qdroid.anime.ui.details.AnimeDetailsScreen
import com.qdroid.anime.ui.home.HomeScreen

data object HomeRoute : NavKey
data class AnimeDetailsRoute(val id: Int) : NavKey

@Composable
fun MainNavigation() {

    val backStack = remember { mutableStateListOf<Any>(HomeRoute) }

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = { key ->
            when (key) {
                is HomeRoute -> NavEntry(key) {
                    HomeScreen(onNavigateToDetails = {
                        backStack.add(
                            AnimeDetailsRoute(it)
                        )
                    })
                }
                is AnimeDetailsRoute -> NavEntry(key) {
                    AnimeDetailsScreen(id = key.id)
                }
                else -> NavEntry(Unit) { Text("Unknown route") }
            }
        }
    )
}