package me.manulorenzo.mapthecity.city

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_city_list_layout.landscapeLayout
import kotlinx.android.synthetic.main.activity_item_list.toolbar
import me.manulorenzo.mapthecity.R
import me.manulorenzo.mapthecity.city.data.Coordinates

class CityListActivity : AppCompatActivity() {
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var twoPane: Boolean = false

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
        // TODO Fix the problem with the home up button
        supportFragmentManager.addOnBackStackChangedListener {
            if (supportFragmentManager.backStackEntryCount > 0) {
                supportActionBar?.setDisplayShowHomeEnabled(true)
            } else {
                supportActionBar?.setDisplayShowHomeEnabled(false)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    fun loadCityMapFragmentWhenClicked(coordinates: Coordinates) {
        if (twoPane) {
            // It's two pane - we load the fragment
            val fragment =
                supportFragmentManager.findFragmentByTag(CityMapFragment::class.java.simpleName) as CityMapFragment
            fragment.onCityChanged(coordinates)
        } else {
            // it's NOT two pane so replace the current fragment by the MapFragment
            val fragment = CityMapFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(CityMapFragment.CITY_COORDINATES_KEY, coordinates)
                }
            }
            supportFragmentManager
                .beginTransaction()
                .add(R.id.frameLayout, fragment)
                .addToBackStack(null)
                .commit()
        }
    }
}
