package com.ets.pomozi.util

import android.content.Context
import android.util.Base64
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide

fun setPhoto(context: Context, destination: ImageView, photoString: String, @DrawableRes placeholder: Int?) {
    val photoByteArray: ByteArray = Base64.decode(photoString, Base64.DEFAULT)

    if (placeholder != null) {
        Glide.with(context)
            .asBitmap()
            .load(photoByteArray)
            .placeholder(placeholder)
            .into(destination)
    } else {
        Glide.with(context)
            .asBitmap()
            .load(photoByteArray)
            .into(destination)
    }
}