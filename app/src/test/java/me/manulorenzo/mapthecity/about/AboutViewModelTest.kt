package me.manulorenzo.mapthecity.about

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
import me.manulorenzo.mapthecity.data.Resource
import me.manulorenzo.mapthecity.data.source.Repository
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class AboutViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()
    @get:Rule
    val coroutineTestRule = CoroutinesTestRule()

    @Test
    fun `aboutInfo should emit first loading and then success value`() = runBlockingTest {
        val observer: Observer<Resource<AboutInfo>> = mock()
        val fakeSuccessResource: Resource.Success<AboutInfo> = Resource.Success(
            AboutInfo(
                companyName = "Fake company name",
                companyAddress = "Jacob Bontiusplaats 9",
                companyPostal = "1018 LL",
                companyCity = "Amsterdam",
                aboutInfo = "This is the Backbase assignment for Android engineering positions."
            )
        )
        val repositoryMock: Repository = mock()

        val sut =
            AboutViewModel(repositoryMock)
        doAnswer { fakeSuccessResource }.whenever(repositoryMock).getAbout()

        sut.aboutInfo.observeForever(observer)
        sut.aboutInfo

        val captor = argumentCaptor<Resource<AboutInfo>>()
        captor.run {
            verify(observer, times(2)).onChanged(capture())
            assertEquals(fakeSuccessResource.data, lastValue.data)
        }
    }

    @Test
    fun `aboutInfo should emit first loading and then error value`() = runBlockingTest {
        val observer: Observer<Resource<AboutInfo>> = mock()
        val fakeErrorResource: Resource.Error<AboutInfo> = Resource.Error("Error")
        val repositoryMock: Repository = mock()

        val sut =
            AboutViewModel(repositoryMock)
        doAnswer { fakeErrorResource }.whenever(repositoryMock).getAbout()

        sut.aboutInfo.observeForever(observer)
        sut.aboutInfo

        val captor = argumentCaptor<Resource<AboutInfo>>()
        captor.run {
            verify(observer, times(2)).onChanged(capture())
            assertEquals(fakeErrorResource.data, lastValue.data)
        }
    }
}