package me.manulorenzo.citymaps.city

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.city_list_row.view.aboutButton
import kotlinx.android.synthetic.main.city_list_row.view.cityNameTextView
import kotlinx.android.synthetic.main.city_list_row.view.coordinatesTextView
import kotlinx.android.synthetic.main.city_list_row.view.countryCodeTextView
import me.manulorenzo.citymaps.R
import me.manulorenzo.citymaps.about.AboutActivity
import me.manulorenzo.citymaps.city.data.City

class CityListAdapter(
    private val parentActivity: CityListActivity,
    private val cityList: List<City>,
    private val twoPane: Boolean
) : RecyclerView.Adapter<CityListAdapter.CityViewHolder>(), Filterable {
    private var filteredCityList: List<City> = cityList
    private val onClickListener: View.OnClickListener
    private val onButtonClickListener: View.OnClickListener = View.OnClickListener {
        parentActivity.startActivity(
            Intent(
                parentActivity,
                AboutActivity::class.java
            )
        )
    }

    init {
        onClickListener = View.OnClickListener { v ->
            val city = v.tag as City
            if (twoPane) {
                // It's two pane - we load the fragment
                val fragment = CityMapFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable(CityMapFragment.CITY_COORDINATES_KEY, city.coordinates)
                    }
                }
                parentActivity.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.mapFragment, fragment)
                    .commit()
            } else {
                // it's NOT two pane so replace the current fragment by the MapFragment
                val fragment = CityMapFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable(CityMapFragment.CITY_COORDINATES_KEY, city.coordinates)
                    }
                }
                parentActivity.supportFragmentManager
                    .beginTransaction()
                    .add(R.id.frameLayout, fragment)
                    .commit()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.city_list_row, parent, false)
        return CityViewHolder(view)
    }

    override fun onBindViewHolder(holderCity: CityViewHolder, position: Int) {
        val city = filteredCityList[position]
        holderCity.cityName.text = city.name
        holderCity.cityCountryCode.text = city.country
        // TODO Improve
        holderCity.cityCoordinates.text =
            city.coordinates.lat.toString() + ", " + city.coordinates.lon
        holderCity.aboutButton.setOnClickListener(onButtonClickListener)
        with(holderCity.itemView) {
            tag = city
            setOnClickListener(onClickListener)
        }
    }

    override fun getItemCount() = filteredCityList.size

    inner class CityViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cityName: TextView = view.cityNameTextView
        val cityCountryCode: TextView = view.countryCodeTextView
        val cityCoordinates: TextView = view.coordinatesTextView
        val aboutButton: Button = view.aboutButton
    }

    override fun getFilter(): Filter = object : Filter() {
        override fun performFiltering(charSequence: CharSequence): FilterResults {
            val charString = charSequence.toString()
            filteredCityList = if (charString.isEmpty()) {
                cityList
            } else {
                val filteredList: MutableList<City> = arrayListOf()
                for (row in cityList) {
                    // TODO Improve
                    if (row.name.toLowerCase().contains(charString.toLowerCase()) ||
                        row.country.contains(charSequence)
                    ) {
                        filteredList.add(row)
                    }
                }
                filteredList
            }
            val filterResults = FilterResults()
            filterResults.values = filteredCityList
            return filterResults
        }

        override fun publishResults(
            charSequence: CharSequence,
            filterResults: FilterResults
        ) {
            filteredCityList = filterResults.values as List<City>
            notifyDataSetChanged()
        }
    }
}