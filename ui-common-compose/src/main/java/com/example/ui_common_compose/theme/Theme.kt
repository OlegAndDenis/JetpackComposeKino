package com.example.ui_common_compose.theme

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

private val LightColors = lightColors(
    primary = Color.White,
    onPrimary = Color.Black,
    secondary = ShinyOrange,
    onSecondary = Color.White,
    background = SmokyWhite,
    surface = SmokyWhite,
    onBackground = WetAsphalt,
    onSurface = WetAsphalt
)

private val DarkColors = darkColors(
    primary = Color.Black,
    onPrimary = Color.White,
    secondary = ShinyOrange,
    onSecondary = Color.Black,
    background = Color.Black,
    surface = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White
)