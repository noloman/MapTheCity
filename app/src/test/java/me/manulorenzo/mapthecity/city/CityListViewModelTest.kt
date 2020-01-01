package me.manulorenzo.mapthecity.city

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import me.manulorenzo.mapthecity.CoroutinesTestRule
import me.manulorenzo.mapthecity.city.data.City
import me.manulorenzo.mapthecity.city.data.Coordinates
import me.manulorenzo.mapthecity.data.Resource
import me.manulorenzo.mapthecity.data.source.Repository
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CityListViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()
    @get:Rule
    val coroutineTestRule = CoroutinesTestRule()

    @Test
    fun `allCities should emit first loading and then a Resource#Success value`() =
        runBlockingTest {
            val fakeSuccessResource = Resource.Success(
                listOf(
                    City(
                        1,
                        "UK",
                        "London",
                        Coordinates(34.5, 56.2)
                    )
                )
            )
            val observer: Observer<Resource<List<City>>> = mock()
            val repositoryMock: Repository = mock()

            val sut =
                CityListViewModel(repositoryMock)
            doAnswer { fakeSuccessResource }.whenever(repositoryMock).getCities()

            sut.allCities.observeForever(observer)
            sut.allCities
            val captor = argumentCaptor<Resource<List<City>>>()
            captor.run {
                verify(observer, times(2)).onChanged(capture())
                assertEquals(fakeSuccessResource.data, lastValue.data)
            }
        }

    @Test
    fun `allCities should emit first loading and then a Resource#Error value`() =
        runBlockingTest {
            val fakeErrorResource = Resource.Error<List<City>>("Error")
            val observer: Observer<Resource<List<City>>> = mock()
            val repositoryMock: Repository = mock()

            val sut =
                CityListViewModel(repositoryMock)
            doAnswer { fakeErrorResource }.whenever(repositoryMock).getCities()

            sut.allCities.observeForever(observer)
            sut.allCities
            val captor = argumentCaptor<Resource<List<City>>>()
            captor.run {
                verify(observer, times(2)).onChanged(capture())
                assertEquals(fakeErrorResource.data, lastValue.data)
            }
        }
}