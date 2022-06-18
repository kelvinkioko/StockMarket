package compose.stockmarket.presentation.company_info

import compose.stockmarket.domain.model.CompanyInfoModel
import compose.stockmarket.domain.model.IntraDayInfoModel

data class CompanyInfoState(
    val isLoading: Boolean = false,
    val company: CompanyInfoModel? = null,
    val intraDayInfo: List<IntraDayInfoModel> = emptyList(),
    val error: String? = null
)
