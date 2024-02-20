package com.ets.pomozi.models

import android.os.Parcel
import android.os.Parcelable

data class DonationModel (
    var id: Int,
    var donatedTo: OrganizationModel,
    var donatedAmount: Float,
    var points: Int,
    var date: String,
    var timestamp: String,
    var user: UserModel
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readParcelable(OrganizationModel::class.java.classLoader)!!,
        parcel.readFloat(),
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readParcelable(UserModel::class.java.classLoader)!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeParcelable(donatedTo, flags)
        parcel.writeFloat(donatedAmount)
        parcel.writeInt(points)
        parcel.writeString(date)
        parcel.writeString(timestamp)
        parcel.writeParcelable(user, flags)
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