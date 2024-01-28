package com.ets.pomozi.models

import android.os.Parcel
import android.os.Parcelable

data class DonationModel (
    var id: Int,
    var donatedTo: String,
    var donatedAmount: Float,
    var points: Int,
    var date: String,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readFloat(),
        parcel.readInt(),
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(donatedTo)
        parcel.writeFloat(donatedAmount)
        parcel.writeInt(points)
        parcel.writeString(date)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DonationModel> {
        override fun createFromParcel(parcel: Parcel): DonationModel {
            return DonationModel(parcel)
        }

        override fun newArray(size: Int): Array<DonationModel?> {
            return arrayOfNulls(size)
        }
    }
}