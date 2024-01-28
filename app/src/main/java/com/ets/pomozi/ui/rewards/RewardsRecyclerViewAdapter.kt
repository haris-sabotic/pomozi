package com.ets.pomozi.ui.rewards

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ets.pomozi.R
import android.view.ViewGroup
import android.view.LayoutInflater
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.request.RequestOptions
import com.ets.pomozi.models.RewardModel
import jp.wasabeef.glide.transformations.BlurTransformation

class RewardsRecyclerViewAdapter (
    private val context: Context,
    private val dataSet: ArrayList<RewardModel>,
) :
    RecyclerView.Adapter<RewardsRecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val photo: ImageView
        val textTitle: TextView
        val textDescription: TextView
        val textPrice: TextView

        init {
            photo = view.findViewById(R.id.item_rewards_photo)
            textTitle = view.findViewById(R.id.item_rewards_text_title)
            textDescription = view.findViewById(R.id.item_rewards_text_description)
            textPrice = view.findViewById(R.id.item_rewards_text_price)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_rewards, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val reward = dataSet[position]

        viewHolder.textTitle.text = reward.title
        viewHolder.textDescription.text = reward.description
        viewHolder.textPrice.text = reward.price.toString()

        Glide.with(context)
            .load(reward.photo)
            .apply(RequestOptions.bitmapTransform(BlurTransformation(8, 2)))
            .into(viewHolder.photo)
    }

    override fun getItemCount() = dataSet.size
}
