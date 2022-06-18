package compose.stockmarket.data.csv

import com.opencsv.CSVReader
import compose.stockmarket.domain.model.CompanyListingModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CompanyListingParser @Inject constructor() : CSVParser<CompanyListingModel> {

    override suspend fun parse(stream: InputStream): List<CompanyListingModel> {
        val csvReader = CSVReader(InputStreamReader(stream))
        return withContext(Dispatchers.IO) {
            csvReader
                .readAll()
                .drop(1)
                .mapNotNull { line ->
                    val symbol = line.getOrNull(0)
                    val name = line.getOrNull(1)
                    val exchange = line.getOrNull(2)
                    val assetType = line.getOrNull(3)
                    val ipoDate = line.getOrNull(4)
                    CompanyListingModel(
                        symbol = symbol ?: return@mapNotNull null,
                        name = name ?: return@mapNotNull null,
                        exchange = exchange ?: return@mapNotNull null,
                        assetType = assetType ?: return@mapNotNull null,
                        ipoDate = ipoDate ?: return@mapNotNull null,
                    )
                }
                .also {
                    csvReader.close()
                }
        }
    }
}
