package compose.stockmarket.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "company_listing")
data class CompanyListingEntity(
    @PrimaryKey val id: Int? = null,
    var symbol: String,
    var name: String,
    var exchange: String,
    var assetType: String,
    var ipoDate: String
)
