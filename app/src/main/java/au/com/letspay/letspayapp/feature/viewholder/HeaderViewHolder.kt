package au.com.letspay.letspayapp.feature.viewholder

import android.view.View
import android.widget.TextView
import au.com.letspay.letspayapp.R
import au.com.letspay.letspayapp.feature.model.Header
import butterknife.BindView
import butterknife.ButterKnife

/**
 * Created by chowii on 2/4/18.
 */
class HeaderViewHolder(itemView: View) : BaseViewHolder<Header>(itemView) {

    @BindView(R.id.date_text_view) lateinit var dateTextView: TextView
    @BindView(R.id.duration_text_view) lateinit var durationTextView: TextView

    init {
        ButterKnife.bind(this, itemView)
    }

    override fun onBindViewHolder(item: Header, position: Int) {
        dateTextView.text = item.header
        durationTextView.text = item.subheader
    }
}