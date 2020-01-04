package me.manulorenzo.mapthecity.data.source

import android.app.Application
import android.content.res.AssetManager
import com.google.gson.JsonSyntaxException
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import me.manulorenzo.mapthecity.city.data.City
import me.manulorenzo.mapthecity.data.Resource
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.ByteArrayInputStream

@ExperimentalCoroutinesApi
class RepositoryTest {
    private lateinit var sut: Repository
    private val applicationMock = mock<Application>()
    private val assetsMock = mock<AssetManager>()
    @Before
    fun setUp() {
        sut = RepositoryImpl(applicationMock)
    }

    @Test
    fun `given a list of cities it retrieves them inside a Resource#Success`() {
        runBlockingTest {
            doAnswer { assetsMock }.whenever(applicationMock).assets
            doAnswer {
                ByteArrayInputStream(
                    """[{"country":"ES","name":"Osuna","_id":6361023,"coord":{"lon":-5.11371,"lat":37.22229}}]""".trimIndent().toByteArray(
                        Charsets.UTF_8
                    )
                )
            }
                .whenever(assetsMock)
                .open(any())
            val cities = sut.getCities()
            assertTrue(cities is Resource.Success<List<City>>)
            assertTrue(cities.data?.size == 1)
        }
    }

    @Test
    fun `given a list of cities it retrieves them inside a Resource#Error`() {
        runBlockingTest {
            doAnswer { assetsMock }.whenever(applicationMock).assets
            doThrow(JsonSyntaxException::class)
                .whenever(assetsMock)
                .open(any())
            val cities = sut.getCities()
            assertTrue(cities is Resource.Error<List<City>>)
            assertNull(cities.data)
        }
    }
}