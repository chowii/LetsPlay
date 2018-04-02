package au.com.letspay.letspayapp.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import au.com.letspay.letspayapp.database.LetsPayDatabase.Contract.Companion.TABLE_TRANSACTION
import au.com.letspay.letspayapp.feature.model.UserTransaction

/**
 * Created by chowii on 2/4/18.
 */
@Dao
interface LetsPlayDao {

    @Query("SELECT * FROM $TABLE_TRANSACTION")
    fun getTransaction(): MutableList<UserTransaction>

    @Insert(onConflict = REPLACE)
    fun insertOne(transaction: UserTransaction): Long

}