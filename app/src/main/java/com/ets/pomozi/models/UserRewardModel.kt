package com.ets.pomozi.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class UserRewardModel (
    var count: Int,
    var reward: RewardModel
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readParcelable(RewardModel::class.java.classLoader)!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(count)
        parcel.writeParcelable(reward, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserRewardModel> {
        override fun createFromParcel(parcel: Parcel): UserRewardModel {
            return UserRewardModel(parcel)
        }

        override fun newArray(size: Int): Array<UserRewardModel?> {
            return arrayOfNulls(size)
        }
    }
}
