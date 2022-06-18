package compose.stockmarket.domain.repository

import compose.stockmarket.domain.model.CompanyInfoModel
import compose.stockmarket.domain.model.CompanyListingModel
import compose.stockmarket.domain.model.IntraDayInfoModel
import compose.stockmarket.util.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {

    suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        searchQuery: String
    ): Flow<Resource<List<CompanyListingModel>>>

    suspend fun getCompanyInfo(
        symbol: String
    ): Resource<CompanyInfoModel>

    suspend fun getCompanyIntraDayInfo(
        symbol: String
    ): Resource<List<IntraDayInfoModel>>
}
