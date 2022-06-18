package compose.stockmarket.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import compose.stockmarket.data.local.dao.StockDao

@Database(
    entities = [CompanyListingEntity::class],
    version = 1
)
abstract class StockDatabase: RoomDatabase() {
    abstract val dao: StockDao
}