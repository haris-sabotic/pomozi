package com.ets.pomozi.ui.profile

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ets.pomozi.R
import com.ets.pomozi.models.UserRewardModel
import com.ets.pomozi.util.GlobalData
import jp.wasabeef.glide.transformations.BlurTransformation

class UserRewardsRecyclerViewAdapter(
    private val context: Context,
    private val dataSet: ArrayList<UserRewardModel>,
) : RecyclerView.Adapter<UserRewardsRecyclerViewAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val root: ConstraintLayout
        val photo: ImageView
        val textTitle: TextView
        val textDescription: TextView
        val textPrice: TextView

        init {
            root = view.findViewById(R.id.item_rewards_root)
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

        viewHolder.textTitle.text = reward.reward.title
        viewHolder.textDescription.text = reward.reward.description
        viewHolder.textPrice.text = "${reward.count}X" // use count instead of price

        Glide.with(context)
            .load(GlobalData.PHOTO_PREFIX + reward.reward.photo)
            .apply(RequestOptions.bitmapTransform(BlurTransformation(8, 2)))
            .into(viewHolder.photo)
    }

    override fun getItemCount() = dataSet.size
}