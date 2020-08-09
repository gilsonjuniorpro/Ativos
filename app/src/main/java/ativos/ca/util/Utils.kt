package ativos.ca.util

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
    }
}