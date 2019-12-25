package me.manulorenzo.citymaps.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class City(val country: String, val name: String, val id: Int, val coordinates: Coordinates) :
    Parcelable