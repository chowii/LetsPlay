package au.com.letspay.letspayapp.feature.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import au.com.letspay.letspayapp.R
import au.com.letspay.letspayapp.feature.model.BaseModel
import au.com.letspay.letspayapp.feature.model.Header
import au.com.letspay.letspayapp.feature.model.UserTransaction
import au.com.letspay.letspayapp.feature.viewholder.BaseViewHolder
import au.com.letspay.letspayapp.feature.viewholder.HeaderViewHolder
import au.com.letspay.letspayapp.feature.viewholder.UserTransactionViewHolder

/**
 * Created by chowii on 2/4/18.
 */
class LetsPayAdapter(
        private val dataset: MutableList<BaseModel>,
        private val onAtmClickListener: (String?) -> Unit
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    override fun getItemCount(): Int = dataset.size

    override fun getItemViewType(position: Int): Int = dataset[position].itemViewType

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder<*> {
        val itemView = LayoutInflater.from(parent?.context).inflate(viewType, parent, false)
        return when (viewType) {
            R.layout.item_header -> HeaderViewHolder(itemView)
            R.layout.item_transaction -> UserTransactionViewHolder(itemView, onAtmClickListener)
            else -> super.createViewHolder(parent, viewType)
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>?, position: Int) {
        when(holder) {
            is UserTransactionViewHolder -> holder.onBindViewHolder(dataset[position] as UserTransaction, position)
            is HeaderViewHolder -> holder.onBindViewHolder(dataset[position] as Header, position)
        }
    }
}