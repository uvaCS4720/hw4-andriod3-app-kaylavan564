package edu.nd.pmcburne.hello

class LocationRepository (
    private val dao: LocationDao
) {
    suspend fun syncLocations() {
        val apiLocations = RetrofitInstance.api.getLocations()
        val entities = apiLocations.map {
            LocationEntity(
                id = it.id,
                name = it.name,
                description = it.description,
                latitude = it.visual_center.latitude,
                longitude = it.visual_center.longitude,
                tags = it.tag_list.joinToString(",")

            )
        }
        dao.insertAll(entities)

    }
    suspend fun getLocationsByTag(tag:String): List<LocationEntity>{
        return dao.getLocationsByTag(tag)

    }
    suspend fun getAllLocations(): List<LocationEntity>{
        return dao.getAllLocations()
    }

    fun getAllTags(locations: List<LocationEntity>): List<String> {
        return locations.flatMap { it.tags.split(",") }.map { it.trim() }.distinct().sorted()
    }


}