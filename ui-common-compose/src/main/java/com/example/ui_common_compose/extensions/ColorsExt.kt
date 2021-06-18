package com.example.ui_common_compose.extensions

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.ui_common_compose.theme.BlackBrown
import com.example.ui_common_compose.theme.WetAsphalt

val Colors.textField: Color
    @Composable get() = if (isLight) Color.White else BlackBrown

val Colors.textFieldLabel: Color
    @Composable get() = if (isLight) WetAsphalt else Color.White