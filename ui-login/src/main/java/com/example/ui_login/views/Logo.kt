package com.example.ui_login.views

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@ExperimentalFoundationApi
@Composable
internal fun Logo() {
    LazyVerticalGrid(cells = GridCells.Fixed(4), modifier = Modifier.size(140.dp)) {
        this.items("VERYGOODLOGOHERE".toCharArray().toList()) { item ->
            Box(modifier = Modifier.size(35.dp), contentAlignment = Alignment.Center) {
                Text(
                    text = item.toString(),
                    style = MaterialTheme.typography.h4,
                    color = MaterialTheme.colors.secondary,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}