package me.manulorenzo.mapthecity.city

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.rule.ActivityTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import me.manulorenzo.mapthecity.CustomAssertions.toolbarUpButtonExists
import me.manulorenzo.mapthecity.CustomAssertions.withRecyclerViewOfSize
import me.manulorenzo.mapthecity.CustomAssertions.withRowContaining
import me.manulorenzo.mapthecity.R
import me.manulorenzo.mapthecity.data.source.FakeCitiesRepository
import me.manulorenzo.mapthecity.data.source.Repository
import me.manulorenzo.mapthecity.data.source.ServiceLocator
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@MediumTest
@RunWith(AndroidJUnit4::class)
class CityListFragmentTest {
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
    fun givenCityListFragmentLoadedIntoActivity_itShouldGoToAnotherFragmentWhenRowIsClickedOnRecyclerView() {
        testRule.launchActivity(null)

        assertTrue(testRule.activity.supportFragmentManager.backStackEntryCount == 0)
        onView(withId(R.id.item_list))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<CityListAdapter.CityViewHolder>(
                    0,
                    click()
                )
            )

        assertTrue(testRule.activity.supportFragmentManager.backStackEntryCount == 1)
    }

    @Test
    fun givenCityListFragment_toolbarShouldBeDisplayed_alongWithSearchIconButNoUpButton() {
        testRule.launchActivity(null)

        onView(withId(R.id.toolbar)).check(matches(isDisplayed()))
        onView(withText(R.string.app_name)).check(matches(withParent(withId(R.id.toolbar))))
        onView(withId(R.id.action_search)).check(matches(isDisplayed()))
        // The navigate up button should NOT be displayed as a child of the toolbar
        !toolbarUpButtonExists()
    }

    @Test
    fun givenListOfCities_shouldHaveTheRecyclerViewVisible() {
        testRule.launchActivity(null)
        onView(withId(R.id.item_list)).check(matches(isDisplayed()))
    }

    @Test
    fun givenListOfCities_andCertainName_shouldOnlyShowOneCity() {
        val sevilleCity = "Seville"
        testRule.launchActivity(null)

        performSearch(sevilleCity)

        onView(withId(R.id.item_list)).check(withRowContaining(withText(sevilleCity)))
    }

    @Test
    fun givenListOfCities_andWrongName_shouldOnlyShowNoCity() {
        testRule.launchActivity(null)

        performSearch("dasfdasdf")

        onView(withId(R.id.item_list)).check(withRecyclerViewOfSize(0))
    }

    private fun performSearch(searchTerm: String) {
        onView(withId(R.id.action_search)).perform(click())
        onView(withId(R.id.search_src_text)).perform(ViewActions.typeText(searchTerm))
    }
}