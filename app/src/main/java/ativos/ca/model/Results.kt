package ativos.ca.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
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
    val updatedAt: String?,
    var paid: String?,
    var broking: String?,
    var profit: String?
): Parcelable{

}