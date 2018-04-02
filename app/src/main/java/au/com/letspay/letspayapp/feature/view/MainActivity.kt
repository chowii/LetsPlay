package au.com.letspay.letspayapp.feature.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import au.com.letspay.letspayapp.R

/**
 * Created by chowii on 3/4/18.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startActivity(Intent(this, LetsPayActivity::class.java))
    }
}