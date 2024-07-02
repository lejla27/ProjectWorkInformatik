package com.example.projectworkmap.data

import kotlinx.coroutines.flow.Flow

interface RouteRepository {
    fun getAllRoutesStream(): Flow<List<Route>>

    /**
     * Retrieve an item from the given data source that matches with the [id].
     */
    fun getRouteStream(id: Int): Flow<Route?>

    /**
     * Insert item in the data source
     */
    suspend fun insertRoute(item: Route)

    /**
     * Delete item from the data source
     */
    suspend fun deleteRoute(item: Route)

    /**
     * Update item in the data source
     */
    suspend fun updateRoute(item: Route)
}