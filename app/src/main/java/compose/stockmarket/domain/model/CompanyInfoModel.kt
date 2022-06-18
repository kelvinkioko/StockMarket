package compose.stockmarket.domain.model

data class CompanyInfoModel(
    val symbol: String,
    val name: String,
    val description: String,
    val country: String,
    val sector: String,
    val industry: String,
    val address: String
)
