package au.com.cognizant.cognizantdev.network

import au.com.letspay.letspayapp.network.HttpErrorType
import java.io.IOException

class HttpStatusException(private var error: List<String>?, var errorType: HttpErrorType) : IOException() {

    fun getMessageString(): String {
        var message = ""
        error?.forEach { message = it.plus(", ").plus(it) }
        return message
    }

}
