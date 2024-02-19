package com.ets.pomozi.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class OrganizationModel(
    val name: String,
    @SerializedName("donated_to_name")
    val donatedToName: String,
    val description: String,
    val url: String,

    val photo: String,
    @SerializedName("featured_photo")
    val featuredPhoto: String?,

    @SerializedName("lat")
    val latitude: String,
    @SerializedName("lng")
    val longitude: String,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString(),
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(donatedToName)
        parcel.writeString(description)
        parcel.writeString(url)
        parcel.writeString(photo)
        parcel.writeString(featuredPhoto)
        parcel.writeString(latitude)
        parcel.writeString(longitude)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OrganizationModel> {
        override fun createFromParcel(parcel: Parcel): OrganizationModel {
            return OrganizationModel(parcel)
        }

        override fun newArray(size: Int): Array<OrganizationModel?> {
            return arrayOfNulls(size)
        }
    }
}
