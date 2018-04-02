package au.com.letspay.letspayapp.network

import au.com.letspay.letspayapp.feature.model.LetsPayModel
import io.reactivex.Observable
import retrofit2.http.GET

interface LetsPayService {

    @GET("data.json?dl=1")
    fun getDataset(): Observable<LetsPayModel>

}
