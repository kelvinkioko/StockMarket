package compose.stockmarket.data.remote

import compose.stockmarket.data.remote.dto.CompanyInfoDto
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface StockApi {
    @GET(value = "query")
    suspend fun getCompanyListings(
        @Query(value = "function") function: String = LISTING_STATUS,
        @Query(value = "apikey") apiKey: String = API_KEY
    ): ResponseBody

    @GET(value = "query")
    suspend fun getCompanyInfo(
        @Query(value = "function") function: String = OVERVIEW,
        @Query(value = "symbol") symbol: String,
        @Query(value = "apikey") apiKey: String = API_KEY
    ): CompanyInfoDto

    @GET(value = "query")
    suspend fun getCompanyIntraDayInfo(
        @Query(value = "function") function: String = TIME_SERIES_INTRADAY,
        @Query(value = "symbol") symbol: String,
        @Query(value = "interval") interval: String = SIXTY_MIN,
        @Query(value = "apikey") apiKey: String = API_KEY,
        @Query(value = "datatype") datatype: String = CSV
    ): ResponseBody

    companion object {
        const val BASE_URL = "https://www.alphavantage.co/"
        const val TIME_SERIES_INTRADAY = "TIME_SERIES_INTRADAY"
        const val LISTING_STATUS = "LISTING_STATUS"
        const val OVERVIEW = "OVERVIEW"
        const val API_KEY = "ZN8T5YLN71YNTJSH"
        const val CSV = "csv"

        const val ONE_MIN = "1min"
        const val FIVE_MIN = "5min"
        const val FIFTEEN_MIN = "15min"
        const val THIRTY_MIN = "30min"
        const val SIXTY_MIN = "60min"
    }
}
