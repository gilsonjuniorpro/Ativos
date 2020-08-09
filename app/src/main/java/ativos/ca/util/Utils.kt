package ativos.ca.util

import ativos.ca.model.Results
import ativos.ca.model.Stock
import java.text.DecimalFormat

class Utils {
    companion object {
        fun getColorIndicator(color: String?): Int{
            return when (color) {
                "#333333" ->  1
                "#fdbe3b" -> 2
                "#ff4842" -> 3
                "#3a52fc" -> 4
                else -> 5
            }
        }

        fun getPartString(text: String?): String?{
            return if(text!!.length > 80){
                text.substring(0, 80) + "..."
            }else{
                text
            }
        }

        fun formatNumberCurrency(number: String?) : String{
            var formatter = DecimalFormat("###,###,##0.00")
            return if(number == null){
                "R$ 0.00"
            }else{
                "R$ " + formatter.format(number.toDouble())
            }
        }

        fun formatNumberPercent(number: String?) : String{
            return if(number == null){
                "0%"
            }else{
                 "$number%"
            }
        }

        fun stockToResults(stock: Stock): Results{
            return Results(
                    id = stock.id,
                    symbol = stock.symbol,
                    name = stock.name,
                    paid = stock.paid,
                    broking = stock.broking,
                    profit = stock.profit,
                    amount = stock.amount
                )
        }

        fun resultsToStock(results: Results): Stock{
            return Stock(
                    id = results.id,
                    symbol = results.symbol,
                    name = results.name,
                    paid = results.paid,
                    broking = results.broking,
                    profit = results.profit,
                    amount = results.amount
                )
        }
    }
}