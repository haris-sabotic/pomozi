package com.ets.pomozi.models

import android.os.Parcel
import android.os.Parcelable

data class UserModel (
    var id: Int,
    var name: String,
    var email: String,
    var phone: String,
    var about: String,
    var photo: String,
    var donatedAmount: Float,
    var points: Int,
    var achievements: AchievementsModel
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readFloat(),
        parcel.readInt(),
        parcel.readParcelable(AchievementsModel::class.java.classLoader)!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(email)
        parcel.writeString(phone)
        parcel.writeString(about)
        parcel.writeString(photo)
        parcel.writeFloat(donatedAmount)
        parcel.writeInt(points)
        parcel.writeParcelable(achievements, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserModel> {
        override fun createFromParcel(parcel: Parcel): UserModel {
            return UserModel(parcel)
        }

        override fun newArray(size: Int): Array<UserModel?> {
            return arrayOfNulls(size)
        }
    }
}