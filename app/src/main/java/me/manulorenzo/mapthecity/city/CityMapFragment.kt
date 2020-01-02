package me.manulorenzo.mapthecity.city

import android.os.Bundle
import android.view.Menu
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import me.manulorenzo.mapthecity.city.data.Coordinates

class CityMapFragment : SupportMapFragment(), OnMapReadyCallback {
    companion object {
        const val CITY_COORDINATES_KEY = "city_coordinates"
    }

    private lateinit var map: GoogleMap
    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        setHasOptionsMenu(true)
        (activity as CityListActivity).supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDefaultDisplayHomeAsUpEnabled(true)
        }
        getMapAsync(this)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.getItem(0).isVisible = false
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