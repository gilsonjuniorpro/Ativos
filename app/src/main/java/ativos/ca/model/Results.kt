package ativos.ca.model

data class Results(
    val change_percent: Double?,
    val currency: String?,
    val market_cap: Double?,
    val name: String?,
    val price: Double?,
    val region: String?,
    val symbol: String?,
    val updated_at: String?
)