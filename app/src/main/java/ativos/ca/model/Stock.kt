package ativos.ca.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "stock")
data class Stock(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var symbol: String? = null,
    var name: String? = null,
    var paid: String? = null,
    var broking: String? = null,
    var profit: String? = null
) : Parcelable {

}