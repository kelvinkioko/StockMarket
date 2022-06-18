package compose.stockmarket.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface StockApi {
    @GET(value = "query")
    suspend fun getCompanyListings(
        @Query(value = "function") function: String = FUNCTION,
        @Query(value = "apikey") apiKey: String = API_KEY
    )

    companion object {
        const val BASE_URL = "https://www.alphavantage.co/"
        const val FUNCTION = "LISTING_STATUS"
        const val API_KEY = "ZN8T5YLN71YNTJSH"
    }
}
