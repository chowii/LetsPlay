package au.com.letspay.letspayapp.feature.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import au.com.letspay.letspayapp.LetsPayContract
import au.com.letspay.letspayapp.R
import au.com.letspay.letspayapp.feature.adapter.LetsPayAdapter
import au.com.letspay.letspayapp.feature.model.Atm
import au.com.letspay.letspayapp.feature.model.BaseModel
import au.com.letspay.letspayapp.feature.model.UserAccount
import au.com.letspay.letspayapp.feature.presenter.LetsPayPresenter
import au.com.letspay.letspayapp.network.ApiClient
import butterknife.BindView
import butterknife.ButterKnife

class LetsPayActivity : AppCompatActivity(), LetsPayContract.LetsPayInteractable {

    companion object {
        @JvmField
        val TAG: String = this::class.java.simpleName
    }

    @BindView(R.id.recyclerView) lateinit var recyclerView: RecyclerView
    @BindView(R.id.swipeRefreshLayout) lateinit var swipeRefreshLayout: SwipeRefreshLayout
    @BindView(R.id.account_name_text_view) lateinit var accountNameTextView : TextView
    @BindView(R.id.account_number_text_view) lateinit var accountNumberTextView : TextView
    @BindView(R.id.available_funds_amount_text_view) lateinit var availableFundsAmountTextView : TextView
    @BindView(R.id.available_balance_amount_text_view) lateinit var availableBalanceAmountTextView : TextView

    private lateinit var presenter: LetsPayPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lets_pay)
        ButterKnife.bind(this)
        presenter = LetsPayPresenter(ApiClient.getService(), this)
        presenter.startPresenting()
        swipeRefreshLayout.setOnRefreshListener { presenter.refreshDataset() }
    }

    override fun showLoading() {
        swipeRefreshLayout.isRefreshing = true
    }

    override fun hideLoading() {
        swipeRefreshLayout.isRefreshing = false
    }

    override fun updateAccountDetails(account: UserAccount) {
        with(account) {
            accountNameTextView.text = accountName
            accountNumberTextView.text = accountNumber
            availableFundsAmountTextView.text = displayableFunds
            availableBalanceAmountTextView.text = displayableBalance
        }
    }

    override fun updateDataset(dataSet: MutableList<BaseModel?>) {
        recyclerView.apply {
            val letsPayDataSet = dataSet.mapNotNull { it }.toMutableList()
            adapter = LetsPayAdapter(letsPayDataSet, onAtmClickListener)
            layoutManager = LinearLayoutManager(this@LetsPayActivity)
        }
    }

    override fun showMap(atm: Atm) {
        with(atm) {
            val mapsUri = Uri.parse("geo:${location.lat}, ${location.lng}?q=${Uri.parse(name)}")
            val mapIntent = Intent(Intent.ACTION_VIEW, mapsUri).apply {
                `package` = "com.google.android.apps.maps"
            }
            startActivity(mapIntent)
        }
    }

    override fun showError(throwable: Throwable) {
        Toast.makeText(this, getString(R.string.error_message), Toast.LENGTH_SHORT).show()
        Log.e(TAG, "showError(): ", throwable)
    }

    private val onAtmClickListener: (String?) -> Unit = {
        presenter.showAtm(it)
    }
}
