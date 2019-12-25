package me.manulorenzo.citymaps.entity

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CityDao {
    @Query("SELECT * FROM city")
    fun getAll(): LiveData<List<CityEntity>>

    @Query("SELECT * FROM city WHERE id IN (:ids)")
    fun loadAllByIds(ids: IntArray): List<CityEntity>

    @Query(
        "SELECT * FROM city WHERE name LIKE :name AND " +
                "country LIKE :country LIMIT 1"
    )
    fun findByName(name: String, country: String): CityEntity

    @Insert
    fun insertAll(cities: List<CityEntity>)

    @Delete
    fun delete(cityEntity: CityEntity)

    @Query("DELETE FROM city")
    suspend fun deleteAll()
}