package ativos.ca.util

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
    }
}