package me.manulorenzo.citymaps.city.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Coordinates(val lat: Double, val lon: Double) : Parcelable