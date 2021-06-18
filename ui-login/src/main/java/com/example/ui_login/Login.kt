package com.example.ui_login

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
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
import com.example.ui_common_compose.button.KinoButton
import com.example.ui_common_compose.textField.PasswordTextField
import com.example.ui_common_compose.textField.TextField

@ExperimentalFoundationApi
@Composable
fun Login() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 44.dp, end = 44.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(modifier = Modifier.weight(0.34f), contentAlignment = Alignment.Center) {
            Logo()
        }

        Column(modifier = Modifier.weight(0.34f), verticalArrangement = Arrangement.Center) {
            TextField(hint = "email here", label = "EMAIL")

            Spacer(modifier = Modifier.size(24.dp))

            PasswordTextField(
                hint = "password here",
                label = "PASSWORD",
                forgotPassword = {})

            Spacer(modifier = Modifier.size(20.dp))

            KinoButton(text = "LOGIN") {
            }
        }
        Box(modifier = Modifier.weight(0.33f)) {

        }

    }
}

@ExperimentalFoundationApi
@Composable
private fun Logo() {
    LazyVerticalGrid(cells = GridCells.Fixed(4), modifier = Modifier.size(140.dp)) {
        this.items("VERYGOODLOGOHERE".toCharArray().toList()) { item ->
            Box(modifier = Modifier.size(35.dp), contentAlignment = Alignment.Center) {

                Text(
                    item.toString(),
                    style = MaterialTheme.typography.h4,
                    color = MaterialTheme.colors.secondary,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

