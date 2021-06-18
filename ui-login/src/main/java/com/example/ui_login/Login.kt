package com.example.ui_login

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ui_common_compose.button.KinoButton
import com.example.ui_common_compose.textField.PasswordTextField
import com.example.ui_common_compose.textField.TextField

@Composable
fun Login() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 44.dp, end = 44.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(modifier = Modifier.weight(0.34f)) {
            Text(
                text = "KINO",
                style = MaterialTheme.typography.h1,
                color = MaterialTheme.colors.secondary,
            )
        }

        Column(modifier = Modifier.weight(0.34f), verticalArrangement = Arrangement.Center) {
            TextField(hint = "email here", label = "EMAIL") {
            }

            Spacer(modifier = Modifier.size(24.dp))

            PasswordTextField(
                hint = "password here",
                label = "PASSWORD",
                onValueChange = {},
                forgotPassword = {})

            Spacer(modifier = Modifier.size(20.dp))
            
            KinoButton(text = "LOGIN") {
            }
        }
        Box(modifier = Modifier.weight(0.33f)) {

        }

    }
}