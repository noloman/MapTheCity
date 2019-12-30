package me.manulorenzo.citymaps

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class CityListActivityTest {
    private lateinit var repository: Repository
    @get:Rule
    var testRule = ActivityTestRule(CityListActivity::class.java, true, false)
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        repository = FakeCitiesRepository()
        ServiceLocator.repository = repository
    }

    @Test
    fun givenListOfCities_shouldHaveTheRecyclerViewVisible() {
        testRule.launchActivity(null)
        onView(withId(R.id.item_list)).check(matches(isDisplayed()))
    }
}