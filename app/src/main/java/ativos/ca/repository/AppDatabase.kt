package ativos.ca.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ativos.ca.model.Stock

@Database(entities = [Stock::class], version = 1, exportSchema = false)
abstract class AppDatabase() : RoomDatabase() {

    abstract fun getStockDao() : StockDao

    companion object {
        private var instance: AppDatabase? = null

        fun getDatabase(context: Context) : AppDatabase {
            if(instance == null){
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "stocksDb"
                ).build()
            }
            return instance!!
        }

    }
}