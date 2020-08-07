package ativos.ca.controller

import ativos.ca.model.Quotation
import ativos.ca.model.Results
import ativos.ca.service.QuotationService

object QuotationController {
    /*fun listAllQuotations(stock: String?): Quotation? {
        var results: Results? = QuotationService.listAllQuotations(stock)
        var listResults: MutableList<Results>? = ArrayList()
        listResults?.add(results!!)
        var quotation = Quotation(
            listResults
        )
        return quotation
    }*/

    fun listAllQuotations(stock: String?): Results? {
        return QuotationService.listAllQuotations(stock)
    }
}