package com.example.ui_login

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ui_common_compose.button.KinoButton
import com.example.ui_common_compose.extensions.textFieldOnSurface
import com.example.ui_common_compose.textField.PasswordTextField
import com.example.ui_common_compose.textField.TextField
import com.example.ui_common_compose.theme.WhiteAluminum
import com.example.ui_login.views.FloatingButton
import com.example.ui_login.views.Logo

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
        LogoBlock()
        FieldsBlock()
        SocialBlock()
    }
}

@ExperimentalFoundationApi
@Composable
private fun ColumnScope.LogoBlock() {
    Box(modifier = Modifier.Companion.weight(0.34f), contentAlignment = Alignment.Center) {
        Logo()
    }
}

@Composable
private fun ColumnScope.FieldsBlock() {
    Column(modifier = Modifier.Companion.weight(0.34f), verticalArrangement = Arrangement.Center) {
        TextField(hint = "email here", label = "EMAIL")
        Spacer(modifier = Modifier.size(24.dp))
        PasswordTextField(hint = "password here", label = "PASSWORD", forgotPassword = {})
        Spacer(modifier = Modifier.size(20.dp))
        KinoButton(text = "LOGIN")
    }
}

@Composable
private fun ColumnScope.SocialBlock() {
    Column(
        modifier = Modifier.weight(0.33f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        SocialLoginsDivider()
        Spacer(modifier = Modifier.size(20.dp))
        FloatingButtons()
        Spacer(modifier = Modifier.size(32.dp))
        RegisterText()
    }
}

@Composable
private fun FloatingButtons() {
    Row(horizontalArrangement = Arrangement.Center) {
        FloatingButton(imageRes = R.drawable.ic_facebook)
        Spacer(modifier = Modifier.size(24.dp))
        FloatingButton(imageRes = R.drawable.ic_google)
    }
}

@Composable
private fun SocialLoginsDivider() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Divider(
            thickness = 1.dp,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.19f),
            modifier = Modifier.weight(1f)
        )

        Text(
            text = "Social Logins",
            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.textFieldOnSurface
        )

        Divider(
            thickness = 1.dp,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.19f),
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun RegisterText() {
    Text(
        text = "Don't have an account?",
        style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Normal),
        color = WhiteAluminum
    )

    Spacer(modifier = Modifier.size(8.dp))

    Text(
        text = "REGISTER",
        style = MaterialTheme.typography.body2,
        color = MaterialTheme.colors.textFieldOnSurface
    )
}

