package com.ets.pomozi.models

import android.os.Parcel
import android.os.Parcelable

data class RewardModel (
    var title: String,
    var description: String,
    var price: Int,
    var photo: String,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
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
