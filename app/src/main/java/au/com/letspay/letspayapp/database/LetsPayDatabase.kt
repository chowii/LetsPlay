package au.com.letspay.letspayapp.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import au.com.letspay.letspayapp.database.LetsPayDatabase.Contract.Companion.DATABASE_LETS_PAY
import au.com.letspay.letspayapp.feature.model.Atm
import au.com.letspay.letspayapp.feature.model.UserTransaction

/**
 * Created by chowii on 2/4/18.
 */

@Database(entities = [(UserTransaction::class), (Atm::class)], version = 1)
@TypeConverters(
        UserTransactionConvertor::class,
        UserAccountConvertor::class,
        CompletedConvertor::class,
        PendingConvertor::class,
        AtmConvertor::class)
abstract class LetsPayDatabase : RoomDatabase() {

    abstract fun letsPayDao(): LetsPlayDao

    companion object {

        fun buildPersistentLetsPay(context: Context): LetsPayDatabase = Room.databaseBuilder(
                context.applicationContext,
                LetsPayDatabase::class.java,
                DATABASE_LETS_PAY
        ).build()
    }

    class Contract {
        companion object {
            const val DATABASE_LETS_PAY = "LetsPayDatabase.db"
            const val TABLE_LETS_PAY_MODEL = "lets_pay_table"
            const val TABLE_TRANSACTION = "trans_table"
            const val TABLE_ATM = "atm_table"
            const val TABLE_USER_ACCOUNT = "user_account_table"
        }
    }

}


