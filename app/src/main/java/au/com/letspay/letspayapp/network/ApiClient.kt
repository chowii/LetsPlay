package au.com.letspay.letspayapp.network

import au.com.cognizant.cognizantdev.network.AddAuthorizationInterceptor
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

object ApiClient {

    private val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    private val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(AddAuthorizationInterceptor())
            .addInterceptor(loggingInterceptor)
            .addInterceptor(StatusCodeInterceptor())
            .build()

    private var service: LetsPayService? = null

    fun getService(): LetsPayService {
        val retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(Constants.ENDPOINT)
            .addConverterFactory(buildGson())
            .client(okHttpClient)
            .build()
        service = retrofit.create(LetsPayService::class.java)
        return service!!
    }

    fun getServiceWithTimeout(time: Long, timeUnit: TimeUnit): LetsPayService {
        return Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(Constants.ENDPOINT)
                .addConverterFactory(buildGson())
                .client(okHttpClientWithTimeout(time, timeUnit))
                .build()
                .create(LetsPayService::class.java)
    }

    private fun okHttpClientWithTimeout(time: Long, timeUnit: TimeUnit): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(AddAuthorizationInterceptor())
                .addInterceptor(loggingInterceptor)
                .addInterceptor(StatusCodeInterceptor())
                .connectTimeout(time, timeUnit)
                .readTimeout(time, timeUnit)
                .build()
    }

    private fun buildGson(): GsonConverterFactory {
        return GsonConverterFactory.create(GsonBuilder().registerTypeAdapterFactory(DataTypeAdapterFactory()).create())
    }

    class DataTypeAdapterFactory : TypeAdapterFactory {

        override fun <T> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T> {

            val delegate = gson.getDelegateAdapter(this, type)
            val elementAdapter = gson.getAdapter(JsonElement::class.java)

            return object : TypeAdapter<T>() {

                @Throws(IOException::class)
                override fun write(out: JsonWriter, value: T) {
                    delegate.write(out, value)
                }

                @Throws(IOException::class)
                override fun read(`in`: JsonReader): T {

                    var jsonElement = elementAdapter.read(`in`)
                    if (jsonElement.isJsonObject) {
                        val jsonObject = jsonElement.asJsonObject
                        if (jsonObject.has("data"))
                            if (jsonObject.get("data").isJsonObject) {
                                jsonElement = jsonObject.get("data")
                            } else if (jsonObject.get("data").isJsonArray && type.rawType.isAssignableFrom(List::class.java)) {
                                jsonElement = jsonObject.get("data")
                            }
                    }

                    return delegate.fromJsonTree(jsonElement)
                }
            }.nullSafe()
        }
    }
}