package com.qdroid.anime.ui.home

import app.cash.turbine.test
import com.qdroid.anime.domain.model.AnimeMovie
import com.qdroid.anime.domain.model.AnimeMovieDetails
import com.qdroid.anime.domain.model.AnimeRequestType
import com.qdroid.anime.domain.model.PaginatedMovies
import com.qdroid.anime.domain.repository.AnimeRepository
import com.qdroid.anime.domain.usecase.PopularNowUseCase
import com.qdroid.anime.domain.usecase.TrendingNowUseCase
import com.qdroid.anime.utility.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    private val dispatcherProvider = object : DispatcherProvider {
        override val main: CoroutineDispatcher = testDispatcher
        override val io: CoroutineDispatcher = testDispatcher
        override val default: CoroutineDispatcher = testDispatcher
        override val unconfined: CoroutineDispatcher = testDispatcher
    }

    private lateinit var repository: FakeAnimeRepository
    private lateinit var trendingNowUseCase: TrendingNowUseCase
    private lateinit var popularNowUseCase: PopularNowUseCase
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = FakeAnimeRepository()
        trendingNowUseCase = TrendingNowUseCase(repository, dispatcherProvider)
        popularNowUseCase = PopularNowUseCase(repository, dispatcherProvider)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init should load trending and popular movies`() = runTest {
        val trendingMovies = listOf(createAnimeMovie(1, "Trending 1"))
        val popularMovies = listOf(createAnimeMovie(2, "Popular 1"))

        repository.trendingResult = Result.success(PaginatedMovies(1, true, trendingMovies))
        repository.popularResult = Result.success(PaginatedMovies(1, true, popularMovies))

        viewModel = HomeViewModel(trendingNowUseCase, popularNowUseCase)

        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(trendingMovies, state.trendingNow.trendingMovies)
            assertEquals(popularMovies, state.popularNow.popularMovies)
            assertFalse(state.trendingNow.isLoading)
            assertFalse(state.popularNow.isLoading)
        }
    }

    @Test
    fun `LoadMoreTrending intent should load next page`() = runTest {
        repository.trendingResult =
            Result.success(PaginatedMovies(1, true, listOf(createAnimeMovie(1, "T1"))))
        repository.popularResult = Result.success(PaginatedMovies(1, false, emptyList()))

        viewModel = HomeViewModel(trendingNowUseCase, popularNowUseCase)

        val nextTrendingMovies = listOf(createAnimeMovie(3, "Trending 2"))
        repository.trendingResult = Result.success(PaginatedMovies(2, false, nextTrendingMovies))

        viewModel.onIntent(HomeScreenIntent.LoadMoreTrending)

        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(2, state.trendingNow.trendingMovies.size)
            assertEquals("T1", state.trendingNow.trendingMovies[0].title)
            assertEquals("Trending 2", state.trendingNow.trendingMovies[1].title)
            assertEquals(2, state.trendingNow.currentPage)
            assertFalse(state.trendingNow.hasNextPage)
        }
    }

    @Test
    fun `LoadMorePopular intent should load next page`() = runTest {
        repository.trendingResult = Result.success(PaginatedMovies(1, false, emptyList()))
        repository.popularResult =
            Result.success(PaginatedMovies(1, true, listOf(createAnimeMovie(2, "P1"))))

        viewModel = HomeViewModel(trendingNowUseCase, popularNowUseCase)

        val nextPopularMovies = listOf(createAnimeMovie(4, "Popular 2"))
        repository.popularResult = Result.success(PaginatedMovies(2, false, nextPopularMovies))

        viewModel.onIntent(HomeScreenIntent.LoadMorePopular)

        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(2, state.popularNow.popularMovies.size)
            assertEquals("P1", state.popularNow.popularMovies[0].title)
            assertEquals("Popular 2", state.popularNow.popularMovies[1].title)
            assertEquals(2, state.popularNow.currentPage)
            assertFalse(state.popularNow.hasNextPage)
        }
    }

    @Test
    fun `failure should emit error event`() = runTest {
        repository.trendingResult = Result.failure(Exception("Trending Error"))
        repository.popularResult = Result.success(PaginatedMovies())

        viewModel = HomeViewModel(trendingNowUseCase, popularNowUseCase)

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.eventsFlow.collect { event ->
                assertEquals(HomeEvents.OnError("Trending Error"), event)
            }
        }
    }

    private fun createAnimeMovie(id: Int, title: String) = AnimeMovie(
        id = id,
        title = title,
        imageUrl = "",
        score = 80,
        genres = emptyList(),
        duration = 24
    )

    private class FakeAnimeRepository : AnimeRepository {
        var trendingResult: Result<PaginatedMovies> = Result.success(PaginatedMovies())
        var popularResult: Result<PaginatedMovies> = Result.success(PaginatedMovies())

        override suspend fun getAnimeShows(
            requestType: AnimeRequestType,
            page: Int
        ): Result<PaginatedMovies> {
            return when (requestType) {
                AnimeRequestType.Trending -> trendingResult
                AnimeRequestType.Popularity -> popularResult
            }
        }

        override suspend fun getAnimeDetails(id: Int): Result<AnimeMovieDetails> {
            return Result.failure(Exception("Not implemented"))
        }
    }
}
