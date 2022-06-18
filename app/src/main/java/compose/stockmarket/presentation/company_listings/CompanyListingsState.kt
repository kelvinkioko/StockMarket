package compose.stockmarket.presentation.company_listings

import compose.stockmarket.domain.model.CompanyListingModel

data class CompanyListingsState(
    val companies: List<CompanyListingModel> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val searchQuery: String = ""
)
