package ativos.ca.service

import ativos.ca.model.Quotation
import ativos.ca.model.Results
import ativos.ca.util.Constants
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import java.lang.reflect.Type
import java.net.URL


object QuotationService {

    fun listAllQuotations(stock: String): Results? {
        val url = Constants.HGBRASIL_URL + stock
        val retString = URL(url).readText()

        var jsonObj = JSONObject(retString)
        val stockDto: String = jsonObj.get("results").toString()

        jsonObj = JSONObject(stockDto)

        var results = Gson().fromJson(jsonObj.get(stock.toUpperCase()).toString(), Results::class.java)

        return results
    }
}