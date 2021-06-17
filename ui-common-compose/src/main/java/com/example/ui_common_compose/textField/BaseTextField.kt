package com.example.ui_common_compose.textField

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.ui_common_compose.extensions.TextField
import com.example.ui_common_compose.extensions.TextFieldLabel
import com.example.ui_common_compose.theme.WetAsphalt

@Composable
internal fun BaseTextField(
    label: String,
    height: Dp = 44.dp,
    focusElevation: Dp = 8.dp,
    nonFocusElevation: Dp = 2.dp,
    decorationBox: @Composable (
        showHint: Boolean,
        innerTextField: @Composable () -> Unit
    ) -> Unit
) {
    Label(text = label)

    var focus by remember { mutableStateOf(false) }

    val elevation: Dp by animateDpAsState(
        targetValue = if (focus) focusElevation else nonFocusElevation,
        animationSpec = tweenSpec()
    )

    Surface(
        elevation = elevation,
        shape = MaterialTheme.shapes.large,
        color = MaterialTheme.colors.TextField,
        modifier = Modifier
            .onFocusChanged { focus = it.isFocused }
            .fillMaxWidth()
            .height(height)
    ) {
        BaseTextField(decorationBox)
    }
}

@Composable
private fun Label(text: String) = Text(
    text = text,
    textAlign = TextAlign.Start,
    style = MaterialTheme.typography.caption,
    color = MaterialTheme.colors.TextFieldLabel,
    modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 8.dp)
)

@Composable
private fun tweenSpec(): TweenSpec<Dp> = tween(
    durationMillis = 300,
    easing = LinearOutSlowInEasing
)

@Composable
private fun BaseTextField(
    decorationBox: @Composable (
        showHint: Boolean,
        innerTextField: @Composable () -> Unit
    ) -> Unit
) {
    var text by remember { mutableStateOf("") }

    BasicTextField(
        value = text,
        singleLine = true,
        onValueChange = { text = it },
        textStyle = MaterialTheme.typography.subtitle1.copy(color = WetAsphalt),
        decorationBox = { innerTextField ->
            decorationBox(text.isEmpty()) {
                innerTextField()
            }
        },
    )
}