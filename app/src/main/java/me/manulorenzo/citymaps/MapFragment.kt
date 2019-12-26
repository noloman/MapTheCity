package me.manulorenzo.citymaps

import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import me.manulorenzo.citymaps.data.Coordinates

class MapFragment : SupportMapFragment(), OnMapReadyCallback {
    companion object {
        const val CITY_COORDINATES_KEY = "city_coordinates"
    }

    private lateinit var map: GoogleMap
    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        arguments?.let {
            val coordinates: Coordinates? = it.getParcelable(CITY_COORDINATES_KEY)
            val latLng = coordinates?.lat?.let { it1 -> LatLng(it1, coordinates.lon) }
            with(map) {
                addMarker(latLng?.let { it1 -> MarkerOptions().position(it1) })
                moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10.0f))
            }
        }
    }
}