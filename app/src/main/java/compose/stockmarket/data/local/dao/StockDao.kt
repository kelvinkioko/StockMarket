package compose.stockmarket.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import compose.stockmarket.data.local.CompanyListingEntity

@Dao
interface StockDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompanyListings(
        companyListingEntity: CompanyListingEntity
    )

    @Query("DELETE FROM company_listing")
    suspend fun clearCompanyListings()

    @Query(
        """
            SELECT *
            FROM company_listing
            WHERE LOWER(name) LIKE '%' || LOWER(:searchQuery) || '%' 
            OR UPPER(:searchQuery) == symbol
        """
    )
    suspend fun searchCompanyListings(searchQuery: String): List<CompanyListingEntity>
}
