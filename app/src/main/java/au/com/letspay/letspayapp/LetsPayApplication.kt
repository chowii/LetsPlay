package au.com.letspay.letspayapp

import android.app.Application
import au.com.letspay.letspayapp.database.LetsPayDatabase
import au.com.letspay.letspayapp.network.Constants

/**
 * Created by chowii on 30/03/18.
 */
class LetsPayApplication: Application() {

    companion object {
        lateinit var db: LetsPayDatabase
    }

    override fun onCreate() {
        super.onCreate()
        db = LetsPayDatabase.buildPersistentLetsPay(this)
        Constants.init(this)
    }
}