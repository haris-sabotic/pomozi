package com.ets.pomozi.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class RewardModel (
    var id: Int,
    @SerializedName("name")
    var title: String,
    var description: String,
    var price: Int,
    var photo: String,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeInt(price)
        parcel.writeString(photo)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RewardModel> {
        override fun createFromParcel(parcel: Parcel): RewardModel {
            return RewardModel(parcel)
        }

        override fun newArray(size: Int): Array<RewardModel?> {
            return arrayOfNulls(size)
        }
    }
}
