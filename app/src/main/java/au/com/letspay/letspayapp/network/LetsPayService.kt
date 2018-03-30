package au.com.letspay.letspayapp.network

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET

interface LetsPayService {

    @GET("data.json?dl=1")
    fun getDataset(): Observable<Response<Any>>

}
