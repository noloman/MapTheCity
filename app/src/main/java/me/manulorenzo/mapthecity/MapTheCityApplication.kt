package me.manulorenzo.mapthecity

import android.app.Application
import android.os.StrictMode
import android.os.StrictMode.VmPolicy
import me.manulorenzo.mapthecity.data.source.Repository
import me.manulorenzo.mapthecity.data.source.ServiceLocator

class MapTheCityApplication : Application() {
    val repository: Repository
        get() = ServiceLocator.provideCitiesRepository(this)

    override fun onCreate() {
        super.onCreate()
        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build()
        )
        StrictMode.setVmPolicy(
            VmPolicy.Builder()
                .detectAll()
                .penaltyDeath()
                .build()
        )
    }
}