package compose.stockmarket.presentation.company_listings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import compose.stockmarket.domain.repository.StockRepository
import compose.stockmarket.util.Resource
import compose.stockmarket.util.StockConstants.SEARCH_DELAY_MS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyListingsViewModel @Inject constructor(
    private val repository: StockRepository
) : ViewModel() {

    var state by mutableStateOf(CompanyListingsState())

    private var searchJob: Job? = null

    init {
        getCompanyListings()
    }

    fun onEvent(event: CompanyListingsEvent) {
        when (event) {
            CompanyListingsEvent.Refresh ->
                getCompanyListings(fetchFromRemote = true)
            is CompanyListingsEvent.OnSearchQueryChange -> {
                state = state.copy(searchQuery = event.searchQuery)
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(SEARCH_DELAY_MS)
                    getCompanyListings()
                }
            }
        }
    }

    private fun getCompanyListings(
        searchQuery: String = state.searchQuery.lowercase(),
        fetchFromRemote: Boolean = false
    ) {
        viewModelScope.launch {
            repository
                .getCompanyListings(fetchFromRemote = fetchFromRemote, searchQuery = searchQuery)
                .collect { result ->
                    when (result) {
                        is Resource.Error -> Unit
                        is Resource.Loading ->
                            state = state.copy(isLoading = result.isLoading)
                        is Resource.Success ->
                            result.data?.let { listings ->
                                state = state.copy(companies = listings)
                            }
                    }
                }
        }
    }
}
