package compose.stockmarket.data.repository

import compose.stockmarket.data.csv.CSVParser
import compose.stockmarket.data.local.StockDatabase
import compose.stockmarket.data.mapper.toCompanyListingEntity
import compose.stockmarket.data.mapper.toCompanyListingModel
import compose.stockmarket.data.remote.StockApi
import compose.stockmarket.domain.model.CompanyListingModel
import compose.stockmarket.domain.repository.StockRepository
import compose.stockmarket.util.Resource
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

@Singleton
class StockRepositoryImpl @Inject constructor(
    val api: StockApi,
    val database: StockDatabase,
    val companyListingParser: CSVParser<CompanyListingModel>
): StockRepository {

    private val stockDao = database.stockDao

    override suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        searchQuery: String
    ): Flow<Resource<List<CompanyListingModel>>> {
        return flow {
            emit(Resource.Loading(isLoading = true))

            val localCompanyListings = stockDao.searchCompanyListings(searchQuery = searchQuery)
            emit(
                Resource.Success(
                    data = localCompanyListings.map { it.toCompanyListingModel() }
                )
            )

            val isLocalDBEmpty = localCompanyListings.isEmpty() && searchQuery.isBlank()

            val shouldJustReloadFromCache = !isLocalDBEmpty && !fetchFromRemote

            if (shouldJustReloadFromCache) {
                emit(Resource.Loading(isLoading = false))
                return@flow
            }

            val remoteCompanyListings = try {
                val response = api.getCompanyListings()
                companyListingParser.parse(response.byteStream())
            } catch (ioException: IOException) {
                ioException.printStackTrace()
                emit(Resource.Error(message = "Couldn't load data"))
                null
            } catch (httpException: HttpException) {
                httpException.printStackTrace()
                emit(Resource.Error(message = "Couldn't load data"))
                null
            }

            remoteCompanyListings?.let { listings ->
                stockDao.clearCompanyListings()
                stockDao.insertCompanyListings(
                    companyListings = listings.map { it.toCompanyListingEntity() }
                )

                emit(
                    Resource.Success(
                        data = stockDao
                            .searchCompanyListings(searchQuery = searchQuery)
                            .map { it.toCompanyListingModel() }
                    )
                )
                emit(Resource.Loading(isLoading = false))

            }
        }
    }

}