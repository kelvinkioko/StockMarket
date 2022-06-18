package compose.stockmarket.data.mapper

import compose.stockmarket.data.local.CompanyListingEntity
import compose.stockmarket.domain.model.CompanyListingModel

fun CompanyListingEntity.toCompanyListingModel(): CompanyListingModel {
    return CompanyListingModel(
        symbol = symbol,
        name = name,
        exchange = exchange,
        assetType = assetType,
        ipoDate = ipoDate
    )
}

fun CompanyListingModel.toCompanyListingEntity(): CompanyListingEntity {
    return CompanyListingEntity(
        symbol = symbol,
        name = name,
        exchange = exchange,
        assetType = assetType,
        ipoDate = ipoDate
    )
}
