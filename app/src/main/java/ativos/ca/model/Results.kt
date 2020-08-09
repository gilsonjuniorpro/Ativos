package ativos.ca.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Results(
    @SerializedName("change_percent")
    val changePercent: Double? = null,
    val currency: String? = null,
    @SerializedName("market_cap")
    val marketCap: Double? = null,
    val name: String? = null,
    val price: Double? = null,
    val region: String? = null,
    val symbol: String? = null,
    @SerializedName("updated_at")
    val updatedAt: String? = null,
    var paid: Double? = null,
    var broking: Double? = null,
    var profit: Int? = null,
    var amount: Int? = null,
    var id: Int? = null
): Parcelable{

}