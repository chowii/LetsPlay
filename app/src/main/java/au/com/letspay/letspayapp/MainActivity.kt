package au.com.letspay.letspayapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import au.com.letspay.letspayapp.network.ApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ApiClient.getService().getDataset()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {

                            it.body()
                            it.body()

                        },
                        {
                            Log.e("LOG_TAG---", it.message, it)
                        }
                )

    }
}
