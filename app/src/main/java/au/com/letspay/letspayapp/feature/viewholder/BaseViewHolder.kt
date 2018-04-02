package au.com.letspay.letspayapp.feature.viewholder

import android.support.annotation.StringRes
import android.support.v7.widget.RecyclerView
import android.view.View
import au.com.letspay.letspayapp.feature.model.BaseModel

/**
 * Created by chowii on 2/4/18.
 */
abstract class BaseViewHolder<in T: BaseModel>(itemView: View): RecyclerView.ViewHolder(itemView) {

    abstract fun onBindViewHolder(item: T, position: Int)

    fun getString(@StringRes stringRes: Int): String = itemView.context.getString(stringRes)

}