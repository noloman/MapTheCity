package me.manulorenzo.citymaps.city

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_item_list.toolbar
import kotlinx.android.synthetic.main.city_list_layout.citiesProgressBar
import kotlinx.android.synthetic.main.city_list_layout.item_list
import kotlinx.android.synthetic.main.city_list_layout.landscapeLayout
import me.manulorenzo.citymaps.CityMapsApplication
import me.manulorenzo.citymaps.R
import me.manulorenzo.citymaps.city.data.City

class CityListActivity : AppCompatActivity() {
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var twoPane: Boolean = false
    lateinit var adapter: CityListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)
        setSupportActionBar(toolbar)
        toolbar.title = title

        if (landscapeLayout != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            twoPane = true
        }

        val citiesListViewModel =
            CityListViewModelFactory((this.application as CityMapsApplication).repository)
                .create(
                    CityListViewModel::class.java
            )
        citiesListViewModel.allCities.observe(this, Observer { cityList: List<City> ->
            citiesProgressBar.visibility = View.GONE
            item_list.visibility = View.VISIBLE
            adapter = CityListAdapter(
                this,
                cityList,
                twoPane
            )
            item_list.adapter = adapter
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        // Associate searchable configuration with the SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.action_search)
            .actionView as SearchView
        searchView.setSearchableInfo(
            searchManager
                .getSearchableInfo(componentName)
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
        return true
    }
}