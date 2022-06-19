package compose.stockmarket.data.remote.dto

import com.squareup.moshi.Json

data class CompanyInfoDto(
    @field:Json(name = "Symbol") val symbol: String?,
    @field:Json(name = "Name") val name: String?,
    @field:Json(name = "Description") val description: String?,
    @field:Json(name = "Country") val country: String?,
    @field:Json(name = "Sector") val sector: String?,
    @field:Json(name = "Industry") val industry: String?,
    @field:Json(name = "Address") val address: String?
)
