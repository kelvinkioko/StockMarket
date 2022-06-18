package compose.stockmarket.data.remote.dto

import com.squareup.moshi.Json

data class CompanyInfoDto(
    @Json(name = "Symbol") val symbol: String? = null,
    @Json(name = "Name") val name: String? = null,
    @Json(name = "Description") val description: String? = null,
    @Json(name = "Country") val country: String? = null,
    @Json(name = "Sector") val sector: String? = null,
    @Json(name = "Industry") val industry: String? = null,
    @Json(name = "Address") val address: String? = null
)
