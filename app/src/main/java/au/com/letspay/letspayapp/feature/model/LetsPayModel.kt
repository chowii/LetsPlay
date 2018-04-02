package au.com.letspay.letspayapp.feature.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import au.com.letspay.letspayapp.R
import au.com.letspay.letspayapp.util.getCalendar
import com.google.gson.annotations.SerializedName
import java.text.NumberFormat
import java.util.*
import java.util.Calendar.*

/**
 * Created by chowii on 1/04/18.
 */
data class LetsPayModel(
        @SerializedName("account")
        val account: UserAccount,

        @SerializedName("transactions")
        val transaction: List<Completed>,

        @SerializedName("pending")
        val pending: List<Pending>,

        @SerializedName("atms")
        val atms: List<Atm>
)

data class UserAccount(
        @SerializedName("accountName")
        val accountName: String,

        @SerializedName("accountNumber")
        val accountNumber: String,

        @SerializedName("available")
        val available: Int,

        @SerializedName("balance")
        val balance: Int
) : BaseModel {

    override val itemViewType: Int
        get() = 0
}

@Entity(tableName = "transaction")
sealed class UserTransaction(
        @PrimaryKey
        @SerializedName("id")
        val id: String? = null,

        @ColumnInfo(name = "effectiveDate")
        @SerializedName("effectiveDate")
        val effectiveDate: String? = null,

        @ColumnInfo(name = "amount")
        @SerializedName("amount")
        val amount: Float? = null,

        @ColumnInfo(name = "atmid")
        @SerializedName("atmId")
        val atmId: String? = null

) : Comparable<Long>, BaseModel {

    @SerializedName("description")
    var description: String? = null
        get() = field?.replace("<br/>", "\n")

    override fun compareTo(other: Long): Int = when {
        this > other -> 1
        this < other -> -1
        else -> 0
    }

    val effectiveCalendar: Calendar?
        get() = getInstance().apply {
            getCalendar(effectiveDate)
        }

    val displayableEffectiveDate: String?
        get() = String.format(
                Locale.getDefault(),
                "%s %s %s",
                effectiveCalendar?.get(DAY_OF_MONTH),
                effectiveCalendar?.getDisplayName(MONTH, SHORT, Locale.getDefault()),
                effectiveCalendar?.get(YEAR)
        )

    val displayableAmount: String
        get() = NumberFormat.getCurrencyInstance(Locale.getDefault()).format(amount)

    override val itemViewType: Int
        get() = R.layout.item_transaction
}

class Completed : UserTransaction()

class Pending : UserTransaction()

data class Atm(
        @SerializedName("id")
        val id: String,

        @SerializedName("name")
        val name: String,

        @SerializedName("address")
        val address: String,

        @SerializedName("location")
        val location: LatLng

)

data class LatLng(
        @SerializedName("lat")
        val lat: Double,

        @SerializedName("lng")
        val lng: Double
)