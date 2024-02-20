package com.ets.pomozi.ui.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ets.pomozi.R
import com.ets.pomozi.models.OrganizationModel
import com.ets.pomozi.util.GlobalData


class OrganizationsRecyclerViewAdapter (
    private val context: Context,
    private val dataSet: ArrayList<OrganizationModel>,
) :
    RecyclerView.Adapter<OrganizationsRecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val root: ConstraintLayout
        val photo: ImageView
        val textName: TextView
        val textDescription: TextView

        init {
            root = view.findViewById(R.id.item_organization_root)
            photo = view.findViewById(R.id.item_organization_photo)
            textName = view.findViewById(R.id.item_organization_text_title)
            textDescription = view.findViewById(R.id.item_organization_text_description)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_organization, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val organization = dataSet[position]

        viewHolder.textName.text = organization.name
        viewHolder.textDescription.text = organization.description

        Glide.with(context)
            .load(GlobalData.PHOTO_PREFIX + organization.photo)
            .into(viewHolder.photo)

        viewHolder.root.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(organization.url))
            context.startActivity(browserIntent)
        }
    }

    override fun getItemCount() = dataSet.size
}
