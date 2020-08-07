package ativos.ca

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import ativos.ca.model.Stock
import ativos.ca.repository.AppDatabase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        val db = AppDatabase.getDatabase(appContext)
        val dao = db.getStockDao()

        runBlocking {
            val stockTest = Stock(
                1,
                "USIM5",
                "My Book Title",
                "150.00",
                "14.00",
                "5%"
            )

            val rowId = dao.insert(stockTest)
            //assertTrue(rowId > -1)

            val stocks = dao.getAllStocks().first()
            stocks.forEach{
                assertEquals(it.symbol, "USIM5")
            }
/*
            val isFavorite = dao.isFavorite(bookTest.id)
            assertTrue(isFavorite == 1)

            val result = dao.delete(bookTest)
            assertTrue(result == 1)*/
        }
    }
}