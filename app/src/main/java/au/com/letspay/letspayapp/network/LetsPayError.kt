package au.com.letspay.letspayapp.network

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import okhttp3.Response
import java.io.IOException

class LetsPayError {

    @SerializedName("error")
    var error: List<String>? = null

    val message: String
        get() {
            if (error == null) {
                return "Failed to complete request, please try again."
            }
            val message = StringBuilder()
            for (s in error!!)
                message.append(", ").append(s)

            return message.toString()
        }

    companion object {

        fun fromResponse(response: Response?): LetsPayError {
            var error: LetsPayError? = LetsPayError()
            try {
                val errorBodyString = response?.body()!!.string()
                error = fromJson(errorBodyString)
            } catch (e: IOException) {
                e.printStackTrace()
            }

            if (error == null) {
                error = LetsPayError()
            }
            return error
        }

        fun fromJson(json: String): LetsPayError {
            try {
                return Gson().fromJson(json, LetsPayError::class.java)
            } catch (e: Exception) {
                return LetsPayError()
            }

        }
    }

}
