package au.com.letspay.letspayapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import au.com.letspay.letspayapp.network.ApiClient

class MainActivity : AppCompatActivity(), LetsPayContract.LetsPayInteractable {

    private lateinit var presenter: LetsPayPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = LetsPayPresenter(ApiClient.getService())


    }
}
