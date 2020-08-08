package ativos.ca.repository

import androidx.room.*
import ativos.ca.model.Stock
import kotlinx.coroutines.flow.Flow

@Dao
interface StockDao {
    @Query("SELECT * FROM stock ORDER BY name ASC")
    fun getAllStocks(): Flow<List<Stock>>

    @Query("SELECT * FROM stock WHERE symbol = :symbol")
    fun getStock(symbol: String): Stock

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(stock: Stock)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(stock: Stock)

    @Delete
    suspend fun delete(vararg stock: Stock)
}