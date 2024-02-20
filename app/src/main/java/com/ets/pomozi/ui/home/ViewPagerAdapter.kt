package com.ets.pomozi.ui.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ets.pomozi.R
import com.ets.pomozi.models.ActionModel
import com.ets.pomozi.util.GlobalData

class ViewPagerAdapter(
    val context: Context,
    val dataset: List<ActionModel>,
) :
    RecyclerView.Adapter<ViewPagerAdapter.ViewPagerViewHolder>() {

    inner class ViewPagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val root: View
        val textName: TextView
        val textDescription: TextView
        val photo: ImageView

        init {
            root = itemView.findViewById(R.id.item_action_root)
            textName = itemView.findViewById(R.id.item_action_text_title)
            textDescription = itemView.findViewById(R.id.item_action_text_description)
            photo = itemView.findViewById(R.id.item_action_photo)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        return ViewPagerViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_action, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        val action = dataset[position]

        holder.textName.text = action.name
        holder.textDescription.text = action.description

        Glide.with(context)
            .load(GlobalData.PHOTO_PREFIX + action.photo)
            .into(holder.photo)

        if (action.url != null) {
            holder.root.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(action.url))
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}