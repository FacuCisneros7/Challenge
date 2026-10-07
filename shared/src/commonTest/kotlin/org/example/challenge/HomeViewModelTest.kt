package org.example.challenge

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.example.challenge.domain.model.AppResult
import org.example.challenge.domain.model.Match
import org.example.challenge.domain.repository.MatchRepository
import org.example.challenge.ui.home.HomeUiState
import org.example.challenge.ui.home.HomeViewModel
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertIs

class FakeMatchRepository : MatchRepository {
    var shouldReturnError = false
    var matchesToReturn = emptyList<Match>()

    override suspend fun getMatchesByTag(tag: String): AppResult<List<Match>> {
        if (shouldReturnError) {
            return AppResult.Error("Error de red simulado")
        }
        return AppResult.Success(matchesToReturn)
    }

    override suspend fun getMatch(id: String): AppResult<Match?> {
        return AppResult.Success(matchesToReturn.find { it.id == id })
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testHomeViewModelLoadsSuccessState() = runTest(testDispatcher) {
        val fakeRepo = FakeMatchRepository()
        val viewModel = HomeViewModel(fakeRepo)

        testScheduler.advanceUntilIdle()

        val state = viewModel.uiState.value
        assertIs<HomeUiState.Success>(state)
    }
}
