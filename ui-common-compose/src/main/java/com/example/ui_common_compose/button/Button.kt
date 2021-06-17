package com.example.ui_common_compose.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.ui_common_compose.theme.DarkTangerine
import com.example.ui_common_compose.theme.ShinyOrange

@Composable
fun KinoButton(text: String, onClick: () -> Unit) = Button(
    onClick = onClick,
    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
    contentPadding = PaddingValues(),
    modifier = Modifier
        .fillMaxWidth()
        .height(44.dp),
    elevation = ButtonDefaults.elevation(
        defaultElevation = 8.dp,
        pressedElevation = 2.dp,
        disabledElevation = 0.dp
    ),
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(ShinyOrange, DarkTangerine)
                )
            )
    ) {
        Text(
            text = text.uppercase(),
            style = MaterialTheme.typography.button,
            color = MaterialTheme.colors.onSecondary,
        )
    }
}