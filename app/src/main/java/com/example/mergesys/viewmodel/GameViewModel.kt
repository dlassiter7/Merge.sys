package com.example.mergesys.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mergesys.model.GameState
import com.example.mergesys.model.HardwareTier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GameViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(GameState())
    val uiState: StateFlow<GameState> = _uiState.asStateFlow()

    // Used for simpler Click-to-Move / Click-to-Merge
    var selectedCellIndex: Int? by mutableStateOf(null)
        private set

    fun onCellClicked(index: Int) {
        val clickedCell = _uiState.value.grid[index]

        if (selectedCellIndex == null) {
            // Select item
            if (clickedCell.item != null) {
                selectedCellIndex = index
            }
        } else {
            // Attempt drop or merge
            val sourceIndex = selectedCellIndex!!
            if (sourceIndex == index) {
                selectedCellIndex = null // Deselect
                return
            }

            processMoveOrMerge(sourceIndex, targetIndex = index)
            selectedCellIndex = null
        }
    }

    private fun processMoveOrMerge(sourceIndex: Int, targetIndex: Int) {
        _uiState.update { state ->
            val newGrid = state.grid.toMutableList()
            val sourceCell = newGrid[sourceIndex]
            val targetCell = newGrid[targetIndex]

            val sourceItem = sourceCell.item
            val targetItem = targetCell.item

            if (sourceItem != null && targetItem != null && sourceItem == targetItem) {
                // Merge matching tiers
                val nextTier = getNextTier(sourceItem)
                if (nextTier != null) {
                    newGrid[targetIndex] = targetCell.copy(item = nextTier)
                    newGrid[sourceIndex] = sourceCell.copy(item = null)

                    val earnedCycles = sourceItem.level * 10L
                    return@update state.copy(
                        grid = newGrid,
                        cycles = state.cycles + earnedCycles
                    )
                }
            } else if (sourceItem != null && targetItem == null) {
                // Move item to empty spot
                newGrid[targetIndex] = targetCell.copy(item = sourceItem)
                newGrid[sourceIndex] = sourceCell.copy(item = null)
            }

            state.copy(grid = newGrid)
        }
    }

    fun onCompileClicked() {
        _uiState.update { state ->
            val emptyCells = state.grid.filter { it.item == null }
            if (emptyCells.isNotEmpty()) {
                val randomEmptyCell = emptyCells.random()
                val newGrid = state.grid.toMutableList()
                newGrid[randomEmptyCell.id] = randomEmptyCell.copy(item = HardwareTier.SCRAP)
                state.copy(grid = newGrid)
            } else {
                state
            }
        }
    }

    private fun getNextTier(tier: HardwareTier): HardwareTier? {
        val nextLevel = tier.level + 1
        return HardwareTier.values().find { it.level == nextLevel }
    }

    // --- Meta-Game Actions ---
    
    fun purchaseUpgrade(cost: Long, onPurchased: () -> Unit) {
        if (_uiState.value.cycles >= cost) {
            _uiState.update { it.copy(cycles = it.cycles - cost) }
            onPurchased()
        }
    }

    // TODO: Google Mobile Ads SDK integration - Call this after a Rewarded Ad finishes
    fun onSystemOverclockAdWatched() {
        // Implementation for speed up e.g. overclock boolean flow
    }

    // TODO: Monetization - Call this after IAP or Rewarded Ad for high-tier item
    fun onBlackMarketDropReceived() {
        _uiState.update { state ->
            val emptyCells = state.grid.filter { it.item == null }
            if (emptyCells.isNotEmpty()) {
                val newGrid = state.grid.toMutableList()
                newGrid[emptyCells.first().id] = emptyCells.first().copy(item = HardwareTier.NODE) // Drops Tier 3
                state.copy(grid = newGrid)
            } else {
                state
            }
        }
    }
}
