package au.com.letspay.letspayapp.model

import com.google.gson.annotations.SerializedName

/**
 * Created by chowii on 1/04/18.
 */
data class LetsPayModel(
        @SerializedName("account")
        val account: UserAccount,

        @SerializedName("transactions")
        val transaction: List<UserTransaction>,

        @SerializedName("pending")
        val pending: List<UserTransaction>,

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
)

data class UserTransaction(
        @SerializedName("id")
        val id: String,

        @SerializedName("effectiveDate")
        val effectiveDate: String,

        @SerializedName("description")
        val description: String,

        @SerializedName("amount")
        val amount: Int
) : Comparable<String> {
    override fun compareTo(other: String): Int {
        return 0
    }
}

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

data class LatLng (
        @SerializedName("lat")
        val lat: Double,

        @SerializedName("lng")
        val lng: Double
)