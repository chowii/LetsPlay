package au.com.letspay.letspayapp.feature.view

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import au.com.letspay.letspayapp.LetsPayApplication
import au.com.letspay.letspayapp.LetsPayContract
import au.com.letspay.letspayapp.R
import au.com.letspay.letspayapp.feature.adapter.LetsPayAdapter
import au.com.letspay.letspayapp.feature.model.BaseModel
import au.com.letspay.letspayapp.feature.model.UserAccount
import au.com.letspay.letspayapp.feature.presenter.LetsPayPresenter
import au.com.letspay.letspayapp.network.ApiClient
import butterknife.BindView
import butterknife.ButterKnife

class MainActivity : AppCompatActivity(), LetsPayContract.LetsPayInteractable {

    @BindView(R.id.recyclerView) lateinit var recyclerView: RecyclerView
    @BindView(R.id.swipeRefreshLayout) lateinit var swipeRefreshLayout: SwipeRefreshLayout
    @BindView(R.id.textView4) lateinit var accountNameTextView : TextView
    @BindView(R.id.textView5) lateinit var accountNumberTextView : TextView

    private lateinit var presenter: LetsPayPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        presenter = LetsPayPresenter(ApiClient.getService(), this, LetsPayApplication.db.letsPayDao())
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
        accountNameTextView.text = account.accountName
        accountNumberTextView.text = account.accountNumber
    }

    override fun updateDataset(dataSet: MutableList<BaseModel?>) {
        recyclerView.apply {
            val letsPayDataSet = dataSet.mapNotNull { it }.toMutableList()
            adapter = LetsPayAdapter(letsPayDataSet, onAtmClickListener)
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private val onAtmClickListener: (String?) -> Unit = {
        presenter.showAtm(it)
    }
}
