package au.com.letspay.letspayapp

import android.util.Log
import au.com.letspay.letspayapp.network.LetsPayService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by chowii on 1/04/18.
 */
class LetsPayPresenter(private val service: LetsPayService) : LetsPayContract.LetsPayPresentable {

    override fun startPresenting() {
        getData()
    }

    override fun refreshDataset() {
        getData()
    }

    private fun getData() {
        service.getDataset()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            Log.d("LOG_TAG---", it.account.accountNumber)
                        },
                        {
                            Log.e("LOG_TAG---", it.message, it)
                        }
                )
    }

}