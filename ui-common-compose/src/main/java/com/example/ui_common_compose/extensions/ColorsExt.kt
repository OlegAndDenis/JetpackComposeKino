package com.example.ui_common_compose.extensions

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.ui_common_compose.theme.BlackBrown

val Colors.TextFieldColor: Color
    @Composable get() = if (isLight) Color.White else BlackBrown