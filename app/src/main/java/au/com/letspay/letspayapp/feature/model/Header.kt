package au.com.letspay.letspayapp.feature.model

import au.com.letspay.letspayapp.R

/**
 * Created by chowii on 2/4/18.
 */
class Header(var header: String, var subheader: String = "") : BaseModel {

    override val itemViewType: Int
        get() = R.layout.item_header


}