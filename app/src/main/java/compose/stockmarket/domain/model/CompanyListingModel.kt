package compose.stockmarket.domain.model

data class CompanyListingModel(
    var symbol: String,
    var name: String,
    var exchange: String,
    var assetType: String,
    var ipoDate: String
)
