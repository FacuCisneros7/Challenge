package org.example.challenge.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.challenge.domain.model.AppResult
import org.example.challenge.domain.model.Match
import org.example.challenge.domain.repository.MatchRepository

data class HomeRow(
    val title: String,
    val tag: String,
    val matches: List<Match>
)

sealed interface HomeUiState {
    data object Loading : HomeUiState
    data class Success(val rows: List<HomeRow>) : HomeUiState
    data class Error(val message: String) : HomeUiState
}

class HomeViewModel(
    private val matchRepository: MatchRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadHomeData()
    }

    fun loadHomeData() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading

            val categories = listOf(
                "Partidos de la Semana" to "week",
                "Premier League" to "premier",
                "Liga Argentina" to "argentina"
            )

            val rows = mutableListOf<HomeRow>()
            var errorMessage: String? = null

            for ((title, tag) in categories) {
                when (val result = matchRepository.getMatchesByTag(tag)) {
                    is AppResult.Success -> {
                        if (result.data.isNotEmpty()) {
                            rows.add(HomeRow(title, tag, result.data))
                        }
                    }
                    is AppResult.Error -> {
                        errorMessage = result.message
                    }
                }
            }

            if (rows.isNotEmpty()) {
                _uiState.value = HomeUiState.Success(rows)
            } else if (errorMessage != null) {
                _uiState.value = HomeUiState.Error(errorMessage)
            } else {
                _uiState.value = HomeUiState.Success(emptyList())
            }
        }
    }
}
