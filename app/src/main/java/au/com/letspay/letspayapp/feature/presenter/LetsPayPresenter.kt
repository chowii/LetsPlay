package au.com.letspay.letspayapp.feature.presenter

import android.util.Log
import au.com.letspay.letspayapp.LetsPayContract
import au.com.letspay.letspayapp.database.LetsPlayDao
import au.com.letspay.letspayapp.feature.model.BaseModel
import au.com.letspay.letspayapp.feature.model.Header
import au.com.letspay.letspayapp.feature.model.LetsPayModel
import au.com.letspay.letspayapp.feature.model.UserTransaction
import au.com.letspay.letspayapp.network.LetsPayService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by chowii on 1/04/18.
 */
class LetsPayPresenter(
        private val service: LetsPayService,
        private val view: LetsPayContract.LetsPayInteractable,
        private val letsPayDao: LetsPlayDao
) : LetsPayContract.LetsPayPresentable {

    override fun startPresenting() {
        getData()
    }

    override fun refreshDataset() {
        getData()
    }

    private fun getData() {
        view.showLoading()
        service.getDataset()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            view.updateAccountDetails(it.account)
                            configureDataset(it)

                            view.hideLoading()
                        },
                        {
                            view.hideLoading()
                            Log.e("LOG_TAG---", it.message, it)
                        }
                )
    }

    private fun configureDataset(letsPayModel: LetsPayModel?) {
        val tempSet = mutableListOf<UserTransaction>()
        val dataSet = mutableListOf<BaseModel?>()
        letsPayModel?.let {
            val sortedPending = it.pending.sortedBy { it.effectiveCalendar?.timeInMillis }
            val sortedTransaction = it.transaction.sortedByDescending { it.effectiveCalendar?.timeInMillis }
            tempSet.addAll(sortedPending)
            tempSet.addAll(sortedTransaction)
        }

        tempSet.sortByDescending { it.effectiveCalendar?.timeInMillis }

        tempSet.forEach { userTransaction ->
            val header = Header("")
            header.header = userTransaction.displayableEffectiveDate.orEmpty()
            header.subheader = "Some days ago"
            val lastHeader = dataSet.filter { it is Header }
                    .map { it as Header }
                    .lastOrNull { it.header == userTransaction.displayableEffectiveDate }
            if (lastHeader == null) {
                dataSet.add(header)
            }
            dataSet.add(userTransaction)
        }
        Observable.create<Long> { emitter ->
            tempSet.forEach { emitter.onNext(letsPayDao.insertOne(it)) }
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            Log.d("LOG_TAG---", "configureDataset(): $it")
                        },
                        {
                            Log.e("LOG_TAG---", "configureDataset(): ${it.message}", it)
                        }
                )
        view.updateDataset(dataSet)
    }

    override fun showAtm(atmId: String?) {

    }
}
