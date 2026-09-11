package com.example.mergesys.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mergesys.model.GridCell
import com.example.mergesys.viewmodel.GameViewModel

@Composable
fun GameBoardScreen(
    viewModel: GameViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Core Loop Header
        Text(
            text = "CYCLES: ${uiState.cycles}",
            color = MaterialTheme.colorScheme.primary,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 32.dp, top = 32.dp)
        )

        // The 4x4 Cyber Grid
        Box(
            modifier = Modifier
                .aspectRatio(1f)
                .fillMaxWidth()
                .border(2.dp, MaterialTheme.colorScheme.secondary, RoundedCornerShape(8.dp))
                .padding(8.dp)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(uiState.grid) { cell ->
                    HardwareCell(
                        cell = cell,
                        isSelected = viewModel.selectedCellIndex == cell.id,
                        onClick = { viewModel.onCellClicked(cell.id) }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Actions
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { viewModel.onCompileClicked() },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("> COMPILE [SCRAP]", color = MaterialTheme.colorScheme.onPrimary)
            }

            OutlinedButton(
                onClick = { viewModel.onSystemOverclockAdWatched() },
                colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.tertiary)
            ) {
                Text("OVERCLOCK")
            }
        }
    }
}

@Composable
fun HardwareCell(
    cell: GridCell,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .aspectRatio(1f)
            .clickable { onClick() }
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
                shape = RoundedCornerShape(8.dp)
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color.DarkGray.copy(alpha = 0.5f)
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (cell.item != null) {
                Text(
                    text = cell.item.title,
                    color = getTierColor(cell.item.level),
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
fun getTierColor(level: Int): Color {
    return when(level) {
        1 -> Color(0xFFAAAAAA) // Scrap (Gray)
        2 -> Color(0xFF00FF00) // Chip (Green)
        3 -> Color(0xFF00FFFF) // Node (Cyan)
        4 -> Color(0xFFFF00FF) // Core (Magenta)
        5 -> Color(0xFFFFD700) // Quantum (Gold)
        else -> Color.White
    }
}
