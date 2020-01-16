package me.manulorenzo.mapthecity.ui.city.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class City(
    @SerializedName("_id") val id: Int,
    val country: String,
    val name: String,
    @SerializedName("coord")
    val coordinates: Coordinates
) : Parcelable