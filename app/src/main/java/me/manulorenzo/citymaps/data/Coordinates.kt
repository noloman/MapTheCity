package me.manulorenzo.citymaps.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Coordinates(val latitude: Float, val longitude: Float) : Parcelable