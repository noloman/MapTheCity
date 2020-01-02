package me.manulorenzo.mapthecity.city

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_city_list_layout.citiesProgressBar
import kotlinx.android.synthetic.main.fragment_city_list_layout.item_list
import me.manulorenzo.mapthecity.MapTheCityApplication
import me.manulorenzo.mapthecity.R
import me.manulorenzo.mapthecity.about.AboutActivity
import me.manulorenzo.mapthecity.city.data.City
import me.manulorenzo.mapthecity.data.Resource

class CityListFragment : Fragment() {
    lateinit var adapter: CityListAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_city_list_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as CityListActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as CityListActivity).supportActionBar?.setDisplayShowHomeEnabled(false)
        val citiesListViewModel =
            CityListViewModelFactory((this.activity?.application as MapTheCityApplication).repository)
                .create(
                    CityListViewModel::class.java
                )
        citiesListViewModel.allCities.observe(this, Observer { cityList: Resource<List<City>> ->
            when (cityList) {
                is Resource.Loading -> {
                    citiesProgressBar.visibility = View.VISIBLE
                    item_list.visibility = View.GONE
                }
                is Resource.Success -> {
                    citiesProgressBar.visibility = View.GONE
                    item_list.visibility = View.VISIBLE
                    if (cityList.data != null) {
                        adapter = CityListAdapter(
                            this.activity as CityListActivity,
                            cityList.data,
                            cityRowClickedListener,
                            aboutButtonClickedListener
                        )
                        item_list.adapter = adapter
                    }
                }
            }
        })
    }

    private val aboutButtonClickedListener = View.OnClickListener {
        startActivity(Intent(activity, AboutActivity::class.java))
    }

    private val cityRowClickedListener = View.OnClickListener { v ->
        val tag = v?.tag as City
        (activity as CityListActivity).loadCityMapFragmentWhenClicked(tag.coordinates)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        // Associate searchable configuration with the SearchView
        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.action_search)
            .actionView as SearchView
        searchView.setSearchableInfo(
            searchManager
                .getSearchableInfo(activity?.componentName)
        )
        searchView.maxWidth = Int.MAX_VALUE

        // listening to search query text change
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean { // filter recycler view when query submitted
                adapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean { // filter recycler view when text is changed
                adapter.filter.filter(query)
                return false
            }
        })
    }
}