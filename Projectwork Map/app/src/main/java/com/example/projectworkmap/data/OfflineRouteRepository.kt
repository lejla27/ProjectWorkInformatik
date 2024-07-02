package com.example.projectworkmap.data

import kotlinx.coroutines.flow.Flow
class OfflineRouteRepository(private val routeDao: RouteDao) : RouteRepository {
    override fun getAllRoutesStream(): Flow<List<Route>> = routeDao.getAllItems()
    override fun getRouteStream(id: Int): Flow<Route?> = routeDao.getQuestion(id)
    override suspend fun insertRoute(item: Route) = routeDao.insert(item)
    override suspend fun deleteRoute(item: Route) = routeDao.delete(item)
    override suspend fun updateRoute(item: Route) = routeDao.update(item)
}