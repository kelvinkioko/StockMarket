package compose.stockmarket.domain.repository

import compose.stockmarket.domain.model.CompanyListingModel
import compose.stockmarket.util.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {

    suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        searchQuery: String
    ): Flow<Resource<List<CompanyListingModel>>>
}