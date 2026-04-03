package edu.nd.pmcburne.hello

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import edu.nd.pmcburne.hello.LocationEntity

@Dao
interface LocationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(locations: List<LocationEntity>)
    @Query("SELECT * FROM locations")
    suspend fun getAllLocations(): List<LocationEntity>
    @Query("SELECT * FROM locations WHERE tags LIKE '%' || :tag || '%'")
    suspend fun getLocationsByTag(tag: String): List<LocationEntity>
}
