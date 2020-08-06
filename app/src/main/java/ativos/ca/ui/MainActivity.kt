package ativos.ca.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ativos.ca.R
import ativos.ca.controller.QuotationController
import ativos.ca.model.Results
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private var list: MutableList<Results> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        callApiRequest()
    }

    private fun callApiRequest() {
        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                val quotation = QuotationController.listAllQuotations("usim5")

                if(quotation?.results != null) {
                    //list = quotation.results.sortedByDescending { l -> l.symbol } as MutableList<Results>

                    //var size = list.size
                }
            }
        }
    }
}