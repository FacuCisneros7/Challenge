package org.example.challenge.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.challenge.domain.model.AppResult
import org.example.challenge.domain.model.Match
import org.example.challenge.domain.repository.MatchRepository

class SearchViewModel(
    private val matchRepository: MatchRepository
) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    private val _searchResults = MutableStateFlow<List<Match>>(emptyList())
    val searchResults: StateFlow<List<Match>> = _searchResults.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _allMatches = mutableListOf<Match>()

    init {
        loadInitialMatches()
    }

    private fun loadInitialMatches() {
        viewModelScope.launch {
            _isLoading.value = true
            val tags = listOf("week", "month", "year", "argentina", "premier", "champions")
            val loaded = mutableSetOf<Match>()
            for (tag in tags) {
                val result = matchRepository.getMatchesByTag(tag)
                if (result is AppResult.Success) {
                    loaded.addAll(result.data)
                }
            }
            _allMatches.clear()
            _allMatches.addAll(loaded)
            _searchResults.value = _allMatches
            _isLoading.value = false
        }
    }

    fun onQueryChanged(newQuery: String) {
        _query.value = newQuery
        filterMatches(newQuery)
    }

    private fun filterMatches(q: String) {
        if (q.isBlank()) {
            _searchResults.value = _allMatches
        } else {
            val queryLower = q.trim().lowercase()
            _searchResults.value = _allMatches.filter { match ->
                match.homeTeam.lowercase().contains(queryLower) ||
                match.awayTeam.lowercase().contains(queryLower) ||
                match.competition.lowercase().contains(queryLower) ||
                match.stadium.lowercase().contains(queryLower)
            }
        }
    }
}
