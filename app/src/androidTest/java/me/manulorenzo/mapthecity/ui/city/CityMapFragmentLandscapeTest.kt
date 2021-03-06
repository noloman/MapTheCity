package me.manulorenzo.mapthecity.ui.city

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.uiautomator.UiDevice
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
class CityMapFragmentLandscapeTest {
    private lateinit var repository: Repository
    private lateinit var device: UiDevice
    @get:Rule
    var testRule = ActivityTestRule(CityListActivity::class.java, true, false)

    @Before
    fun setup() {
        repository =
            FakeCitiesRepository()
        ServiceLocator.repository = repository
        device =
            UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        device.setOrientationLeft()
    }

    @Test
    fun givenLandscapeActivity_toolbarAndItsDescendantsShouldBeDisplayed() = runBlockingTest {
        testRule.launchActivity(null)
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