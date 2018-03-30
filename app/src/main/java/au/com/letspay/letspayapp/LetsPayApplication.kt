package au.com.letspay.letspayapp

import android.app.Application
import au.com.letspay.letspayapp.network.Constants

/**
 * Created by chowii on 30/03/18.
 */
class LetsPayApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        Constants.init(this)
    }
}