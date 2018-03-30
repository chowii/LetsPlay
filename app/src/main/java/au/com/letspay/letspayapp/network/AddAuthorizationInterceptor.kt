package au.com.cognizant.cognizantdev.network

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class AddAuthorizationInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest = request.newBuilder().build()

        return chain.proceed(newRequest)
    }

}
