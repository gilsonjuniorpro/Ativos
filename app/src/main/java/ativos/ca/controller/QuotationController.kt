package ativos.ca.controller

import ativos.ca.model.Quotation
import ativos.ca.service.QuotationService
import ativos.ca.util.Constants
import com.google.gson.Gson
import java.net.URL

object QuotationController {
    fun listAllQuotations(stock: String): Quotation? {
        return QuotationService.listAllQuotations(stock)
    }
}