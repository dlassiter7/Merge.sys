package com.example.mergesys.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mergesys.viewmodel.GameViewModel

@Composable
fun TheHackScreen(
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
        Text(
            text = "// THE HACK",
            color = MaterialTheme.colorScheme.secondary,
            fontSize = 28.sp,
            fontWeight = FontWeight.Black,
            modifier = Modifier.padding(bottom = 8.dp, top = 32.dp)
        )
        
        Text(
            text = "AVAILABLE CYCLES: ${uiState.cycles}",
            color = MaterialTheme.colorScheme.primary,
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.weight(1f)
        ) {
            item {
                UpgradeNode(
                    title = "Automated Merge Scripts",
                    description = "Periodically merges Tier 1 scrap automatically.",
                    cost = 500,
                    onPurchase = { viewModel.purchaseUpgrade(500) { /* Apply effect */ } }
                )
            }
            item {
                UpgradeNode(
                    title = "Expand Grid Allocation",
                    description = "Unlock 5x5 grid matrix (Coming Soon).",
                    cost = 5000,
                    onPurchase = { viewModel.purchaseUpgrade(5000) { /* Apply effect */ } }
                )
            }
            item {
                // Monetization Hook Example
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Black Market Drop (IAP/Ad)", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onErrorContainer)
                        Text("Instantly drops a Tier 3 [Node] onto the grid.", fontSize = 12.sp, color = MaterialTheme.colorScheme.onErrorContainer)
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = { viewModel.onBlackMarketDropReceived() },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                        ) {
                            Text("ACQUIRE NOW")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun UpgradeNode(
    title: String,
    description: String,
    cost: Long,
    onPurchase: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                Text(text = description, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Button(onClick = onPurchase) {
                Text("-$cost CYCLES")
            }
        }
    }
}
