package me.manulorenzo.mapthecity.ui.about

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.rule.ActivityTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
class AboutActivityTest {
    private lateinit var repository: Repository
    @get:Rule
    var testRule = ActivityTestRule(AboutActivity::class.java, true, false)
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        repository =
            FakeCitiesRepository()
        ServiceLocator.repository = repository
    }

    @Test
    fun givenAboutInfo_itShouldShowTheInformationOnTheScreen() {
        testRule.launchActivity(null)

        onView(withId(R.id.companyName)).check(matches(isDisplayed()))
        onView(withId(R.id.companyAdress)).check(matches(withText("Jacob Bontiusplaats 9")))
        onView(withId(R.id.companyCity)).check(matches(withText("Amsterdam")))
        onView(withId(R.id.companypostal)).check(matches(withText("1018 LL")))
        onView(withId(R.id.aboutInfo)).check(matches(withText("This is the Backbase assignment for Android engineering positions.")))
    }
}