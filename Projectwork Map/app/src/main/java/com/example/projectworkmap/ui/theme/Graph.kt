package com.example.projectworkmap.ui.theme

import java.util.PriorityQueue

class Graph {
    private val adjacencyList: MutableMap<String, MutableList<Edge>> = mutableMapOf()

    fun addEdge(source: String, destination: String, weight: Int) {
        adjacencyList.putIfAbsent(source, mutableListOf())
        adjacencyList[source]?.add(Edge(destination, weight))
        adjacencyList.putIfAbsent(destination, mutableListOf())
        adjacencyList[destination]?.add(Edge(source, weight))
    }

    fun getNeighbors(node: String): List<Edge>? {
        return adjacencyList[node]
    }


    fun dijkstra(start: String): Map<String, Int> {
        val distances = mutableMapOf<String, Int>().withDefault { Int.MAX_VALUE }
        distances[start] = 0
        val priorityQueue = PriorityQueue(compareBy<Pair<String, Int>> { it.second })
        priorityQueue.add(start to 0)

        while (priorityQueue.isNotEmpty()) {
            val (currentNode, currentDistance) = priorityQueue.poll()!!
            getNeighbors(currentNode)?.forEach { edge ->
                val newDistance = currentDistance + edge.weight
                if (newDistance < distances.getValue(edge.destination)) {
                    distances[edge.destination] = newDistance
                    priorityQueue.add(edge.destination to newDistance)
                }
            }
        }

        return distances
    }

    fun findShortestPath(start: String, end: String): List<String> {
        val distances = dijkstra(start)
        val path = mutableListOf<String>()
        var currentNode = end

        while (currentNode != start) {
            path.add(currentNode)
            val neighbors = getNeighbors(currentNode) ?: break
            currentNode = neighbors.minByOrNull { distances.getValue(it.destination) + it.weight }?.destination ?: break
        }

        path.add(start)
        path.reverse()
        return path
    }
}