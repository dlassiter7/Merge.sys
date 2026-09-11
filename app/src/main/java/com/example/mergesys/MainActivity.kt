package com.example.mergesys

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.mergesys.ui.GameBoardScreen
import com.example.mergesys.ui.TheHackScreen
import com.example.mergesys.ui.theme.MergesysTheme
import com.example.mergesys.viewmodel.GameViewModel

class MainActivity : ComponentActivity() {
    private val gameViewModel: GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MergesysTheme {
                MainScreen(gameViewModel)
            }
        }
    }
}

@Composable
fun MainScreen(viewModel: GameViewModel) {
    var currentScreen by remember { mutableStateOf(Screen.BOARD) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = currentScreen == Screen.BOARD,
                    onClick = { currentScreen = Screen.BOARD },
                    label = { Text("The Grid") },
                    icon = { Text("G") } // Use icons in real implementation
                )
                NavigationBarItem(
                    selected = currentScreen == Screen.HACK,
                    onClick = { currentScreen = Screen.HACK },
                    label = { Text("The Hack") },
                    icon = { Text("H") }
                )
            }
        }
    ) { innerPadding ->
        val modifier = Modifier.padding(innerPadding)
        when (currentScreen) {
            Screen.BOARD -> GameBoardScreen(viewModel, modifier)
            Screen.HACK -> TheHackScreen(viewModel, modifier)
        }
    }
}

enum class Screen {
    BOARD, HACK
}
