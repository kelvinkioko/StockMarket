package compose.stockmarket.di

import compose.stockmarket.data.csv.CSVParser
import compose.stockmarket.data.csv.CompanyListingParser
import compose.stockmarket.data.csv.IntraDayInfoParser
import compose.stockmarket.data.repository.StockRepositoryImpl
import compose.stockmarket.domain.model.CompanyListingModel
import compose.stockmarket.domain.model.IntraDayInfoModel
import compose.stockmarket.domain.repository.StockRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCompanyListingsParser(
        companyListingParser: CompanyListingParser
    ): CSVParser<CompanyListingModel>

    @Binds
    @Singleton
    abstract fun bindIntraDayInfoParser(
        intraDayInfoParser: IntraDayInfoParser
    ): CSVParser<IntraDayInfoModel>

    @Binds
    @Singleton
    abstract fun bindStockRepository(
        stockRepositoryImpl: StockRepositoryImpl
    ): StockRepository
}
