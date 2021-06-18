package com.example.ui_common_compose.textField

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ui_common_compose.extensions.textFieldLabel
import com.example.ui_common_compose.theme.WhiteAluminum

@Composable
fun TextField(hint: String, label: String, onValueChange: (String) -> Unit = {}) =
    BaseTextField(label = label, onValueChange = onValueChange) { showHint, innerTextField ->
        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp)
                .fillMaxWidth(),
        ) {
            if (showHint) {
                InputText(hint)
            }
            innerTextField()
        }
    }

@Composable
fun PasswordTextField(
    hint: String,
    label: String,
    onValueChange: (String) -> Unit = {},
    forgotPassword: () -> Unit = {}
) = BaseTextField(label = label, onValueChange = onValueChange) { showHint, innerTextField ->
    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp)
            .fillMaxSize(),
    ) {
        if (showHint) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                InputText(hint, Modifier.weight(1f))
                Text(
                    text = "FORGOT",
                    style = MaterialTheme.typography.overline,
                    color = MaterialTheme.colors.textFieldLabel,
                    modifier = Modifier.clickable { forgotPassword() }
                )
            }
        }
        innerTextField()
    }
}

@Composable
private fun InputText(text: String, modifier: Modifier = Modifier) = Text(
    text = text,
    style = MaterialTheme.typography.subtitle1,
    color = WhiteAluminum,
    modifier = modifier
)



