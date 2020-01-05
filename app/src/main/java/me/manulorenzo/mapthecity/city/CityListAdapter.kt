package me.manulorenzo.mapthecity.city

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.city_item_row.view.aboutButton
import kotlinx.android.synthetic.main.city_item_row.view.cityNameTextView
import kotlinx.android.synthetic.main.city_item_row.view.coordinatesTextView
import kotlinx.android.synthetic.main.city_item_row.view.countryCodeTextView
import me.manulorenzo.mapthecity.R
import me.manulorenzo.mapthecity.city.data.City
import java.util.Locale

class CityListAdapter(
    private val cityList: List<City>,
    private val onClickListener: View.OnClickListener,
    private val onButtonClickListener: View.OnClickListener
) : RecyclerView.Adapter<CityListAdapter.CityViewHolder>(), Filterable {
    private var filteredCityList: List<City> = cityList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.city_item_row, parent, false)
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
                    if (row.name.toLowerCase(Locale.getDefault()).contains(
                            charString.toLowerCase(
                                Locale.getDefault()
                            )
                        )
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