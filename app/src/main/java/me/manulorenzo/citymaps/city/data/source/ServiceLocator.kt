package me.manulorenzo.citymaps.city.data.source

import android.app.Application
import android.content.Context
import androidx.annotation.VisibleForTesting

object ServiceLocator {
    @Volatile
    var repository: Repository? = null
        @VisibleForTesting set

    fun provideCitiesRepository(context: Context): Repository {
        synchronized(this) {
            return repository
                ?: createCitiesRepository(
                    context
                )
        }
    }

    private fun createCitiesRepository(context: Context): Repository {
        val citiesRepository =
            CityRepository(context.applicationContext as Application)
        repository = citiesRepository
        return citiesRepository
    }
}