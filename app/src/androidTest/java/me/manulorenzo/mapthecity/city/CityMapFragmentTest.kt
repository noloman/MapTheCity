package me.manulorenzo.mapthecity.city

import android.os.Bundle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.rule.ActivityTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import me.manulorenzo.mapthecity.CustomAssertions.actionSearchIconExists
import me.manulorenzo.mapthecity.CustomAssertions.toolbarUpButtonExists
import me.manulorenzo.mapthecity.R
import me.manulorenzo.mapthecity.data.source.FakeCitiesRepository
import me.manulorenzo.mapthecity.data.source.Repository
import me.manulorenzo.mapthecity.data.source.ServiceLocator
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@MediumTest
@RunWith(AndroidJUnit4::class)
class CityMapFragmentTest {
    private lateinit var repository: Repository
    @get:Rule
    var testRule = ActivityTestRule(CityListActivity::class.java, true, false)

    @Before
    fun setup() {
        repository =
            FakeCitiesRepository()
        ServiceLocator.repository = repository
    }

    @Test
    fun givenCityMapFragment_toolbarAndItsDescendantsShouldBeDisplayed() = runBlockingTest {
        val firstCity = repository.getCities().data?.get(0)
        val fragment = CityMapFragment().apply {
            arguments = Bundle().apply {
                putParcelable(CityMapFragment.CITY_COORDINATES_KEY, firstCity?.coordinates)
            }
        }

        testRule.launchActivity(null)
        testRule.activity.supportFragmentManager
            .beginTransaction()
            .add(R.id.frameLayout, fragment)
            .addToBackStack(null)
            .commit()

        // The toolbar should be displayed
        onView(withId(R.id.toolbar)).check(matches(ViewMatchers.isDisplayed()))
        // The toolbar should have a title
        onView(ViewMatchers.withText(R.string.app_name)).check(
            matches(
                ViewMatchers.withParent(
                    withId(R.id.toolbar)
                )
            )
        )
        // Search icon should not exist
        !actionSearchIconExists()
        // The navigate up button should be displayed as a child of the toolbar
        toolbarUpButtonExists()
    }
}