package com.ets.pomozi.models

import android.os.Parcel
import android.os.Parcelable

data class AchievementsModel (
    var one_year: Boolean,
    var six_months: Boolean,
    var hundred_donated: Boolean,
    var fifty_donated: Boolean,
    var ten_donated: Boolean,
    var ten_donations: Boolean,
    var first_donation: Boolean,
    var ten_rewards_bought: Boolean,
    var five_rewards_bought: Boolean,
    var reward_bought: Boolean,
    var hundred_points_acquired: Boolean,
    var ranked: Boolean,
    var account_created: Boolean,
    var top_three: Boolean,
    var hundred_donations: Boolean,
    var fifty_donations: Boolean
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if (one_year) 1 else 0)
        parcel.writeByte(if (six_months) 1 else 0)
        parcel.writeByte(if (hundred_donated) 1 else 0)
        parcel.writeByte(if (fifty_donated) 1 else 0)
        parcel.writeByte(if (ten_donated) 1 else 0)
        parcel.writeByte(if (ten_donations) 1 else 0)
        parcel.writeByte(if (first_donation) 1 else 0)
        parcel.writeByte(if (ten_rewards_bought) 1 else 0)
        parcel.writeByte(if (five_rewards_bought) 1 else 0)
        parcel.writeByte(if (reward_bought) 1 else 0)
        parcel.writeByte(if (hundred_points_acquired) 1 else 0)
        parcel.writeByte(if (ranked) 1 else 0)
        parcel.writeByte(if (account_created) 1 else 0)
        parcel.writeByte(if (top_three) 1 else 0)
        parcel.writeByte(if (hundred_donations) 1 else 0)
        parcel.writeByte(if (fifty_donations) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AchievementsModel> {
        override fun createFromParcel(parcel: Parcel): AchievementsModel {
            return AchievementsModel(parcel)
        }

        override fun newArray(size: Int): Array<AchievementsModel?> {
            return arrayOfNulls(size)
        }
    }

    fun numberAcquired(): Int {
        var count = 0

        if(one_year) { count += 1 }
        if(six_months) { count += 1 }
        if(hundred_donated) { count += 1 }
        if(fifty_donated) { count += 1 }
        if(ten_donated) { count += 1 }
        if(ten_donations) { count += 1 }
        if(first_donation) { count += 1 }
        if(ten_rewards_bought) { count += 1 }
        if(five_rewards_bought) { count += 1 }
        if(reward_bought) { count += 1 }
        if(hundred_points_acquired) { count += 1 }
        if(ranked) { count += 1 }
        if(account_created) { count += 1 }
        if(top_three) { count += 1 }
        if(hundred_donations) { count += 1 }
        if(fifty_donations) { count += 1 }

        return count
    }
}
