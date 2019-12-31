package me.manulorenzo.citymaps.about

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import me.manulorenzo.citymaps.CoroutinesTestRule
import me.manulorenzo.citymaps.city.data.source.Repository
import me.manulorenzo.citymaps.data.Resource
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

        verify(observer).onChanged(any<Resource.Loading<AboutInfo>>())
        verify(observer).onChanged(fakeSuccessResource)
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

        verify(observer).onChanged(any<Resource.Loading<AboutInfo>>())
        verify(observer).onChanged(fakeErrorResource)
    }
}