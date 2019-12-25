package me.manulorenzo.citymaps

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope
import me.manulorenzo.citymaps.entity.CityDao
import me.manulorenzo.citymaps.entity.CityEntity

@Database(entities = [CityEntity::class], version = 1, exportSchema = false)
abstract class CitiesDatabase : RoomDatabase() {
    abstract fun cityDao(): CityDao

    companion object {
        // Singleton prevents multiple instances of database opening at the same time.
        @Volatile
        private var INSTANCE: CitiesDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): CitiesDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CitiesDatabase::class.java,
                    "cities_database"
                )
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}