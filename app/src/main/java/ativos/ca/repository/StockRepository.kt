package ativos.ca.repository

import android.content.Context
import ativos.ca.model.Stock
import kotlinx.coroutines.flow.Flow

class StockRepository(context: Context) {

    private val stockDao = AppDatabase
        .getDatabase(context).getStockDao()

    suspend fun save(stock: Stock){
        stockDao.insert(stock)
    }

    suspend fun update(stock: Stock){
        stockDao.update(stock)
    }

    suspend fun delete(stock: Stock){
        stockDao.delete(stock)
    }

    fun getAllStocks(): Flow<List<Stock>> {
        return stockDao.getAllStocks()
    }

    fun getStock(symbol: String): Stock {
        return stockDao.getStock(symbol)
    }
}