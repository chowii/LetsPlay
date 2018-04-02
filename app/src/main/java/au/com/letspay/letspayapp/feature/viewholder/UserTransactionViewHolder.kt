package au.com.letspay.letspayapp.feature.viewholder

import android.graphics.Typeface.BOLD
import android.text.SpannableString
import android.text.Spanned.SPAN_COMPOSING
import android.text.style.StyleSpan
import android.view.View
import android.widget.TextView
import au.com.letspay.letspayapp.R
import au.com.letspay.letspayapp.feature.model.Pending
import au.com.letspay.letspayapp.feature.model.UserTransaction
import butterknife.BindView
import butterknife.ButterKnife
import java.util.*

/**
 * Created by chowii on 2/4/18.
 */

class UserTransactionViewHolder(
        itemView: View,
        private val onAtmClickListener: (String?) -> Unit
) : BaseViewHolder<UserTransaction>(itemView) {

    @BindView(R.id.detail_text_view) lateinit var detailTextViewHolder: TextView
    @BindView(R.id.amount_text_view) lateinit var amountTextView: TextView

    init {
        ButterKnife.bind(this, itemView)
    }

    override fun onBindViewHolder(item: UserTransaction, position: Int) {
        if (item is Pending) {
            configurePendingTransaction(item)
        } else {
            detailTextViewHolder.text = item.description
        }
        amountTextView.text = item.displayableAmount
        if (item.atmId != null) {
            amountTextView.apply {
                setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.ic_location,
                        0,
                        0,
                        0)
                setOnClickListener { onAtmClickListener(item.atmId) }
            }
        }
        else
            amountTextView.apply {
                setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                setOnClickListener { }
            }
    }

    private fun configurePendingTransaction(item: UserTransaction) {
        val pendingText = getString(R.string.pending_transaction_text)
        val text = String.format(
                Locale.getDefault(),
                getString(R.string.pending_transaction_format),
                pendingText,
                item.description)
        val spannableString = SpannableString(text)
        spannableString.setSpan(StyleSpan(BOLD), 0, pendingText.length, SPAN_COMPOSING)
        detailTextViewHolder.text = spannableString
    }

}