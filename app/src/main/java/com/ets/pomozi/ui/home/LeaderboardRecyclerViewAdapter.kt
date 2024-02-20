package com.ets.pomozi.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ets.pomozi.R
import com.ets.pomozi.models.UserModel
import com.ets.pomozi.util.GlobalData
import com.ets.pomozi.util.setPhoto

class LeaderboardRecyclerViewAdapter (
    private val context: Context,
    private val leaderboard: ArrayList<UserModel>,
) :
    RecyclerView.Adapter<LeaderboardRecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val photo: ImageView
        val textNumber: TextView
        val textName: TextView
        val textAmount: TextView

        init {
            photo = view.findViewById(R.id.item_leaderboard_photo)
            textNumber = view.findViewById(R.id.item_leaderboard_number)
            textName = view.findViewById(R.id.item_leaderboard_name)
            textAmount = view.findViewById(R.id.item_leaderboard_amount)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_leaderboard, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val user = leaderboard[position+3]

        viewHolder.textName.text = user.name
        viewHolder.textAmount.text = "${user.donatedAmount}€"
        viewHolder.textNumber.text = "${position + 4}"

        setPhoto(context, viewHolder.photo, user.photo, R.drawable.default_user)
        Glide.with(context)
            .load(GlobalData.PHOTO_PREFIX + user.photo)
            .into(viewHolder.photo)
    }

    override fun getItemCount() = if (leaderboard.size <= 3) 0 else (leaderboard.size - 3)
}
