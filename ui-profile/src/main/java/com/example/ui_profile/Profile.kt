package com.example.ui_profile

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.coil.rememberCoilPainter

private const val testImageUrl =
    "https://nypost.com/wp-content/uploads/sites/2/2021/04/elon-musk-010.jpg?quality=80&strip=all"

@Composable
fun Profile() {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.size(80.dp))
        Image()
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            text = "Elon Musk",
            style = MaterialTheme.typography.h4,
            color = MaterialTheme.colors.onSurface
        )
        Spacer(modifier = Modifier.size(4.dp))
        Text(
            text = "Premium",
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.secondary
        )
        Spacer(modifier = Modifier.size(56.dp))
        profileItems.forEachIndexed { index, profileItem ->
            Item(profileItem, index != profileItems.lastIndex)
        }
    }
}


@Composable
private fun Image() {
    Image(
        painter = rememberCoilPainter(request = testImageUrl),
        contentDescription = null,
        modifier = Modifier
            .size(140.dp)
            .clip(CircleShape)
            .border(4.dp, MaterialTheme.colors.secondary, CircleShape),
        contentScale = ContentScale.Crop,
        alignment = Alignment.Center
    )
}

@Composable
private fun Item(item: ProfileItem, setDivider: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(),
                onClick = {}
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(start = 32.dp, end = 32.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(Modifier.size(16.dp))
            ItemIcon(item.icon)
            Spacer(Modifier.size(32.dp))
            Text(
                text = item.content,
                color = MaterialTheme.colors.onSurface,
                style = MaterialTheme.typography.subtitle1
            )
        }
        if (setDivider) ItemDivider()
    }

}

@Composable
private fun ItemDivider() = Surface {
    Divider(
        thickness = 1.dp,
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.19f),
        modifier = Modifier.padding(32.dp, end = 32.dp)
    )
}

@Composable
private fun ItemIcon(@DrawableRes icon: Int) = Icon(
    modifier = Modifier.size(24.dp),
    painter = painterResource(id = icon),
    contentDescription = null,
    tint = MaterialTheme.colors.onSurface
)