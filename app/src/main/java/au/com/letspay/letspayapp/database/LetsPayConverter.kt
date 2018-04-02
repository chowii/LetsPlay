package au.com.letspay.letspayapp.database

import android.arch.persistence.room.TypeConverter
import au.com.letspay.letspayapp.feature.model.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Created by chowii on 2/4/18.
 */
//object LetsPayConverter {

    class UserAccountConvertor {

        @TypeConverter
        fun userAccountListToString(accountList: MutableList<UserAccount>): String = Gson().toJson(accountList)


        @TypeConverter
        fun userAccountStringToList(accountString: String): MutableList<UserAccount> {
            val listType = object : TypeToken<MutableList<UserAccount>>() {}.type
            return Gson().fromJson(accountString, listType)
        }
    }

    class UserTransactionConvertor {

        @TypeConverter
        fun userTransactionListToString(transactionList: MutableList<UserTransaction>): String = Gson().toJson(transactionList)


        @TypeConverter
        fun userTransactionStringToList(jsonString: String): MutableList<UserTransaction> {
            val listType = object : TypeToken<MutableList<UserTransaction>>() {}.type
            return Gson().fromJson(jsonString, listType)
        }
    }

    class CompletedConvertor {

        @TypeConverter
        fun completedListToString(completedList: List<Completed>): String = Gson().toJson(completedList)

        @TypeConverter
        fun completedStringToList(completedString: String): MutableList<Completed> {
            val listType = object : TypeToken<MutableList<Completed>>() {}.type
            return Gson().fromJson(completedString, listType)
        }

    }

    class PendingConvertor {

        @TypeConverter
        fun pendingListToString(pendingList: List<Pending>): String = Gson().toJson(pendingList)

        @TypeConverter
        fun pendingStringToList(pendingString: String): MutableList<Pending> {
            val listType = object : TypeToken<MutableList<Pending>>() {}.type
            return Gson().fromJson(pendingString, listType)
        }

    }

    class AtmConvertor {

        @TypeConverter
        fun atmListToString(atmList: List<Atm>): String = Gson().toJson(atmList)

        @TypeConverter
        fun atmStringToList(atmString: String): MutableList<Atm> {
            val listType = object : TypeToken<MutableList<Atm>>() {}.type
            return Gson().fromJson(atmString, listType)
        }

    }

//}