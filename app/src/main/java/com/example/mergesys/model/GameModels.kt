package com.example.mergesys.model

enum class HardwareTier(val level: Int, val title: String) {
    SCRAP(1, "Scrap"),
    CHIP(2, "Chip"),
    NODE(3, "Node"),
    CORE(4, "Core"),
    QUANTUM(5, "Quantum")
}

data class GridCell(
    val id: Int, // 0 to 15 for 4x4
    val item: HardwareTier? = null
)

data class GameState(
    val grid: List<GridCell> = List(16) { GridCell(it) },
    val cycles: Long = 0,
    val maxGridSize: Int = 16 // 4x4 by default
)
