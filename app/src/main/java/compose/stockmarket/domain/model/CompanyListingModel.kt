package compose.stockmarket.domain.model

data class CompanyListingModel(
    val symbol: String,
    val name: String,
    val exchange: String,
    val assetType: String,
    val ipoDate: String
)
