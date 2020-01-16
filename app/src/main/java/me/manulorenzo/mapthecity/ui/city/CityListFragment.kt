package me.manulorenzo.mapthecity.ui.city

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
import kotlinx.android.synthetic.main.fragment_city_list.citiesProgressBar
import kotlinx.android.synthetic.main.fragment_city_list.citiesRecyclerView
import me.manulorenzo.mapthecity.MapTheCityApplication
import me.manulorenzo.mapthecity.R
import me.manulorenzo.mapthecity.data.Resource
import me.manulorenzo.mapthecity.ui.about.AboutActivity
import me.manulorenzo.mapthecity.ui.city.data.City

class CityListFragment : Fragment() {
    lateinit var adapter: CityListAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_city_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val citiesListViewModel =
            CityListViewModelFactory((this.activity?.application as MapTheCityApplication).repository)
                .create(
                    CityListViewModel::class.java
                )
        citiesListViewModel.allCities.observe(this, Observer { cityList: Resource<List<City>> ->
            when (cityList) {
                is Resource.Loading -> {
                    citiesProgressBar.visibility = View.VISIBLE
                    citiesRecyclerView.visibility = View.GONE
                }
                is Resource.Success -> {
                    citiesProgressBar.visibility = View.GONE
                    citiesRecyclerView.visibility = View.VISIBLE
                    if (cityList.data != null) {
                        adapter = CityListAdapter(
                            cityList.data,
                            cityRowClickedListener,
                            aboutButtonClickedListener
                        )
                        citiesRecyclerView.adapter = adapter
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