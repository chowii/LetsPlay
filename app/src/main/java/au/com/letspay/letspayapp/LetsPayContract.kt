package au.com.letspay.letspayapp

import au.com.letspay.letspayapp.feature.model.Atm
import au.com.letspay.letspayapp.feature.model.BaseModel
import au.com.letspay.letspayapp.feature.model.UserAccount

/**
 * Created by chowii on 1/04/18.
 */
interface LetsPayContract {

    interface LetsPayPresentable {

        fun startPresenting()

        fun refreshDataset()

        fun showAtm(atmId: String?)

    }

    interface LetsPayInteractable {

        fun showLoading()

        fun hideLoading()

        fun showError(throwable: Throwable)

        fun updateAccountDetails(account: UserAccount)

        fun updateDataset(dataSet: MutableList<BaseModel?>)

        fun showMap(atm: Atm)

    }

}