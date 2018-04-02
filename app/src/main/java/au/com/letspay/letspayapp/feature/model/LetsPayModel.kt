package au.com.letspay.letspayapp.feature.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import au.com.letspay.letspayapp.R
import au.com.letspay.letspayapp.database.LetsPayDatabase.Contract.Companion.TABLE_ATM
import au.com.letspay.letspayapp.database.LetsPayDatabase.Contract.Companion.TABLE_LETS_PAY_MODEL
import au.com.letspay.letspayapp.database.LetsPayDatabase.Contract.Companion.TABLE_TRANSACTION
import au.com.letspay.letspayapp.database.LetsPayDatabase.Contract.Companion.TABLE_USER_ACCOUNT
import au.com.letspay.letspayapp.util.getCalendar
import com.google.gson.annotations.SerializedName
import java.text.NumberFormat
import java.util.*
import java.util.Calendar.*

/**
 * Created by chowii on 1/04/18.
 */
@Entity(tableName = TABLE_LETS_PAY_MODEL)
data class LetsPayModel(
        @Ignore
        @SerializedName("account")
        val account: UserAccount,

        @Ignore
        @SerializedName("transactions")
        val transaction: List<Completed>,

        @Ignore
        @SerializedName("pending")
        val pending: List<Pending>,

        @Ignore
        @SerializedName("atms")
        val atms: List<Atm>
)

@Entity(tableName = TABLE_USER_ACCOUNT)
data class UserAccount(
        @Ignore
        @SerializedName("accountName")
        val accountName: String,

        @Ignore
        @SerializedName("accountNumber")
        val accountNumber: String,

        @Ignore
        @SerializedName("available")
        val available: Int,

        @Ignore
        @SerializedName("balance")
        val balance: Int
) : BaseModel {

    override val itemViewType: Int
        get() = 0
}

@Entity(tableName = TABLE_TRANSACTION)
open class UserTransaction(
        @PrimaryKey(autoGenerate = true)
        var tableId: Long,

        @ColumnInfo(name = "transactionId")
        @SerializedName("id")
        var transactionId: String? = null,

        @ColumnInfo(name = "effectiveDate")
        @SerializedName("effectiveDate")
        var effectiveDate: String? = null,

        @ColumnInfo(name = "amount")
        @SerializedName("amount")
        var amount: Float? = null,

        @ColumnInfo(name = "atmId")
        @SerializedName("atmId")
        var atmId: String? = null

) : Comparable<Long>, BaseModel {

    constructor() : this(
            tableId = 0,
            transactionId = "",
            effectiveDate = "",
            amount = 0.0f,
            atmId = ""
    )

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
@Entity(tableName = TABLE_TRANSACTION)
class Completed : UserTransaction()

@Entity(tableName = TABLE_TRANSACTION)
class Pending : UserTransaction()

@Entity(tableName = TABLE_ATM)
open class Atm(
        @ColumnInfo(name = "table_id")
        @PrimaryKey(autoGenerate = true)
        var tableId: Int = -1,

        @ColumnInfo(name = "id")
        @SerializedName("id")
        var id: String,

        @ColumnInfo(name = "name")
        @SerializedName("name")
        var name: String,

        @ColumnInfo(name = "address")
        @SerializedName("address")
        var address: String
//        ,
//        @ColumnInfo(name = "location")
//        @SerializedName("location")
//        var location: LatLng
) {
    constructor() : this(
            id = "",
            name = "",
            address = "")
}

data class LatLng(
        @SerializedName("lat")
        val lat: Double,

        @SerializedName("lng")
        val lng: Double
)