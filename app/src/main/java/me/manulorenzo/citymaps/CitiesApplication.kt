package me.manulorenzo.citymaps

import android.app.Application
import android.os.StrictMode
import android.os.StrictMode.VmPolicy

class CitiesApplication : Application() {
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