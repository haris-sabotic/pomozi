package com.ets.pomozi.ui.profile

import android.content.Context
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ets.pomozi.R
import com.ets.pomozi.models.DonationModel
import android.view.ViewGroup
import android.view.LayoutInflater

class ActivityRecyclerViewAdapter (
    private val context: Context,
    private val dataSet: ArrayList<DonationModel>,
) :
    RecyclerView.Adapter<ActivityRecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val icon: ImageView
        val textMain: TextView
        val textDate: TextView

        init {
            icon = view.findViewById(R.id.item_profile_activity_icon)
            textMain = view.findViewById(R.id.item_profile_activity_text_main)
            textDate = view.findViewById(R.id.item_profile_activity_text_date)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_profile_activity, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val donation = dataSet[position]
        viewHolder.textDate.text = donation.date

        val part1 = "${donation.donatedAmount}€ donirano "
        val part2 = donation.donatedTo
        val mainText = SpannableStringBuilder(part1 + part2)
        mainText.setSpan(
            StyleSpan(Typeface.BOLD),
            part1.length,
            part1.length + part2.length,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )
        viewHolder.textMain.text = mainText

        if (donation.donatedAmount <= 10) {
            viewHolder.icon.setImageResource(R.drawable.medal_bronze)
        } else if (donation.donatedAmount <= 20) {
            viewHolder.icon.setImageResource(R.drawable.medal_silver)
        } else {
            viewHolder.icon.setImageResource(R.drawable.medal_gold)
        }
    }

    override fun getItemCount() = dataSet.size
}
