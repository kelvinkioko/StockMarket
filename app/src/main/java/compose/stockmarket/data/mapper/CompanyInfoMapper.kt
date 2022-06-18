package compose.stockmarket.data.mapper

import compose.stockmarket.data.remote.dto.CompanyInfoDto
import compose.stockmarket.domain.model.CompanyInfoModel

fun CompanyInfoDto.toCompanyInfoModel(): CompanyInfoModel {
    return CompanyInfoModel(
        symbol = symbol ?: "",
        name = name ?: "",
        description = description ?: "",
        country = country ?: "",
        sector = sector ?: "",
        industry = industry ?: "",
        address = address ?: ""
    )
}
