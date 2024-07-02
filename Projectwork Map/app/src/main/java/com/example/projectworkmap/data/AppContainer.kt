package com.example.projectworkmap.data
import android.content.Context

/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val routeRepository: RouteRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val routeRepository: RouteRepository by lazy {
        OfflineRouteRepository(QuizDatabase.getDatabase(context).routeDao())
    }
}