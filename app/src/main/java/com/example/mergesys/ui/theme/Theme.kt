package com.example.mergesys.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val CyberpunkColorScheme = darkColorScheme(
    primary = CyberCyan,
    secondary = CyberMagenta,
    tertiary = CyberYellow,
    background = CyberBlack,
    surface = CyberDark,
    onPrimary = CyberBlack,
    onSecondary = CyberBlack,
    onTertiary = CyberBlack,
    onBackground = CyberCyan,
    onSurface = CyberCyan
)

@Composable
fun MergesysTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = CyberpunkColorScheme // Always use Dark Cyberpunk theme for this game

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
