package au.com.letspay.letspayapp.network

import android.content.Context
import au.com.letspay.letspayapp.R

class Constants {

    companion object {

        var ENDPOINT = ""

        fun init(context: Context) {
            ENDPOINT = context.getString(R.string.endpoint)
        }
    }

}