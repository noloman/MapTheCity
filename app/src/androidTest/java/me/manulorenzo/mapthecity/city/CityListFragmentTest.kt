package me.manulorenzo.mapthecity.city

import android.view.View
import androidx.appcompat.widget.AppCompatImageButton
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.rule.ActivityTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import me.manulorenzo.mapthecity.R
import me.manulorenzo.mapthecity.city.CityListFragmentTest.RecyclerViewAssertions.withRowContaining
import me.manulorenzo.mapthecity.data.source.FakeCitiesRepository
import me.manulorenzo.mapthecity.data.source.Repository
import me.manulorenzo.mapthecity.data.source.ServiceLocator
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher
import org.junit.Assert
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
        onView(Matchers.anyOf(Matchers.instanceOf(AppCompatImageButton::class.java))).check(
            matches(
                Matchers.not(
                    withParent(withId(R.id.toolbar))
                )
            )
        )
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

        onView(withId(R.id.item_list)).check(RecyclerViewAssertions.withRecyclerViewOfSize(0))
    }

    private fun performSearch(searchTerm: String) {
        onView(withId(R.id.action_search)).perform(click())
        onView(withId(R.id.search_src_text)).perform(ViewActions.typeText(searchTerm))
    }

    object RecyclerViewAssertions {
        /**
         * Provides a RecyclerView assertion based on a view matcher. This allows you to
         * validate whether a RecyclerView contains a row in memory without scrolling the list.
         *
         * @param viewMatcher - an Espresso ViewMatcher for a descendant of any row in the recycler.
         * @return an Espresso ViewAssertion to check against a RecyclerView.
         */
        fun withRowContaining(viewMatcher: Matcher<View>): ViewAssertion? {
            Assert.assertNotNull(viewMatcher)
            return ViewAssertion { view, noViewException ->
                if (noViewException != null) {
                    throw noViewException
                }
                assertTrue(view is RecyclerView)
                val recyclerView = view as RecyclerView
                val adapter = recyclerView.adapter
                for (position in 0 until adapter!!.itemCount) {
                    val itemType = adapter.getItemViewType(position)
                    val viewHolder =
                        adapter.createViewHolder(recyclerView, itemType)
                    adapter.bindViewHolder(viewHolder, position)
                    if (viewHolderMatcher(
                            ViewMatchers.hasDescendant(
                                viewMatcher
                            )
                        ).matches(viewHolder)
                    ) {
                        return@ViewAssertion  // Found a matching row
                    }
                }
                Assert.fail("No match found")
            }
        }

        fun withRecyclerViewOfSize(size: Int): ViewAssertion? {
            return ViewAssertion { view, noViewException ->
                if (noViewException != null) {
                    throw noViewException
                }
                assertTrue(view is RecyclerView)
                val recyclerView = view as RecyclerView
                val adapter = recyclerView.adapter
                assertTrue(adapter?.itemCount == size)
            }
        }

        /**
         * Creates matcher for view holder with given item view matcher.
         *
         * @param itemViewMatcher a item view matcher which is used to match item.
         * @return a matcher which matches a view holder containing item matching itemViewMatcher.
         */
        private fun viewHolderMatcher(itemViewMatcher: Matcher<View>): Matcher<RecyclerView.ViewHolder> {
            return object : TypeSafeMatcher<RecyclerView.ViewHolder>() {
                override fun matchesSafely(viewHolder: RecyclerView.ViewHolder?): Boolean {
                    return itemViewMatcher.matches(viewHolder!!.itemView)
                }

                override fun describeTo(description: Description) {
                    description.appendText("holder with view: ")
                    itemViewMatcher.describeTo(description)
                }
            }
        }
    }
}