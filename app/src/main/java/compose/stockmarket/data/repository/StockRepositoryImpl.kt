package compose.stockmarket.data.repository

import compose.stockmarket.data.csv.CSVParser
import compose.stockmarket.data.local.StockDatabase
import compose.stockmarket.data.mapper.toCompanyInfoModel
import compose.stockmarket.data.mapper.toCompanyListingEntity
import compose.stockmarket.data.mapper.toCompanyListingModel
import compose.stockmarket.data.remote.StockApi
import compose.stockmarket.domain.model.CompanyInfoModel
import compose.stockmarket.domain.model.CompanyListingModel
import compose.stockmarket.domain.model.IntraDayInfoModel
import compose.stockmarket.domain.repository.StockRepository
import compose.stockmarket.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
    private val api: StockApi,
    database: StockDatabase,
    private val companyListingParser: CSVParser<CompanyListingModel>,
    private val intraDayInfoParser: CSVParser<IntraDayInfoModel>
) : StockRepository {

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

    override suspend fun getCompanyInfo(symbol: String): Resource<CompanyInfoModel> {
        return try {
            val response = api.getCompanyInfo(symbol = symbol)
            println("Company Info response $response")
            Resource.Success(data = response.toCompanyInfoModel())
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            Resource.Error(message = "Couldn't load company info")
        } catch (httpException: HttpException) {
            httpException.printStackTrace()
            Resource.Error(message = "Couldn't load company info")
        }
    }

    override suspend fun getCompanyIntraDayInfo(symbol: String): Resource<List<IntraDayInfoModel>> {
        return try {
            val response = api.getCompanyIntraDayInfo(symbol = symbol)
            val results = intraDayInfoParser.parse(response.byteStream())
            Resource.Success(data = results)
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            Resource.Error(message = "Couldn't load Intra-day info")
        } catch (httpException: HttpException) {
            httpException.printStackTrace()
            Resource.Error(message = "Couldn't load Intra-day info")
        }
    }
}
