package me.manulorenzo.citymaps

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import me.manulorenzo.citymaps.data.City
import me.manulorenzo.citymaps.data.Coordinates
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CitiesListViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()
    @get:Rule
    val coroutineTestRule = CoroutinesTestRule()

    @Test
    fun `cities should be fetched`() =
        runBlockingTest {
            val cityList = listOf(
                City(
                    1,
                    "UK",
                    "London",
                    Coordinates(34.5, 56.2)
                )
            )
            val observer: Observer<List<City>> = mock()
            val repositoryMock: CitiesRepository = mock()

            val sut = CitiesListViewModel(repositoryMock)
            doAnswer { cityList }.whenever(repositoryMock).getCities()

            sut.allCities.observeForever(observer)
            sut.allCities
            verify(observer).onChanged(cityList)
        }
}