package ativos.ca.model

import com.google.gson.annotations.SerializedName

data class Results(
    @SerializedName("change_percent")
    val changePercent: Double?,
    val currency: String?,
    @SerializedName("market_cap")
    val marketCap: Double?,
    val name: String?,
    val price: Double?,
    val region: String?,
    val symbol: String?,
    @SerializedName("updated_at")
    val updatedAt: String?
)