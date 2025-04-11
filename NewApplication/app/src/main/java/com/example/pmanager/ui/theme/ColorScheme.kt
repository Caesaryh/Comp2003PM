package com.example.pmanager.ui.theme

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// 基础调色板
private val Blue80 = Color(0xFF9DA4FF)
private val Blue40 = Color(0xFF1A237E)
private val Purple80 = Color(0xFFCAB9FF)
private val Purple40 = Color(0xFF4A148C)
private val Teal80 = Color(0xFF80DEEA)
private val Teal40 = Color(0xFF004D40)

// 浅色主题
val LightColorScheme = lightColorScheme(
    primary = Blue40,
    onPrimary = Color.White,
    primaryContainer = Blue80,
    onPrimaryContainer = Blue40,

    secondary = Purple40,
    onSecondary = Color.White,
    secondaryContainer = Purple80,
    onSecondaryContainer = Purple40,

    tertiary = Teal40,
    onTertiary = Color.White,
    tertiaryContainer = Teal80,
    onTertiaryContainer = Teal40,

    background = Color(0xFFFEFBFF),
    onBackground = Color(0xFF1B1B1F),

    surface = Color(0xFFFEFBFF),
    onSurface = Color(0xFF1B1B1F),
    surfaceVariant = Color(0xFFE3E1EC),
    onSurfaceVariant = Color(0xFF46464F),

    outline = Color(0xFF767680),
    inverseSurface = Color(0xFF303034),
)

// 深色主题
val DarkColorScheme = darkColorScheme(
    primary = Blue80,
    onPrimary = Blue40,
    primaryContainer = Color(0xFF303F9F),
    onPrimaryContainer = Blue80,

    secondary = Purple80,
    onSecondary = Purple40,
    secondaryContainer = Color(0xFF6A1B9A),
    onSecondaryContainer = Purple80,

    tertiary = Teal80,
    onTertiary = Teal40,
    tertiaryContainer = Color(0xFF00796B),
    onTertiaryContainer = Teal80,

    background = Color(0xFF1B1B1F),
    onBackground = Color(0xFFE4E1E6),

    surface = Color(0xFF1B1B1F),
    onSurface = Color(0xFFE4E1E6),
    surfaceVariant = Color(0xFF46464F),
    onSurfaceVariant = Color(0xFFC7C5D0),

    outline = Color(0xFF90909A),
    inverseSurface = Color(0xFFE4E1E6),
)


