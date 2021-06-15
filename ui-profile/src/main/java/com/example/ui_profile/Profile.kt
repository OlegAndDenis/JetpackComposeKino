package com.example.ui_profile

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.coil.rememberCoilPainter

private const val testImageUrl =
    "https://nypost.com/wp-content/uploads/sites/2/2021/04/elon-musk-010.jpg?quality=80&strip=all"

@Composable
fun Profile() {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.size(80.dp))
        Image()
        Spacer(modifier = Modifier.size(16.dp))
        Text(text = "Elon Musk", fontSize = 27.sp, color = Color.White)
        Spacer(modifier = Modifier.size(4.dp))
        Text(text = "Premium", fontSize = 20.sp, color = Color(0xFFFFBB3B))
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
            .border(4.dp, Color(0xFFFFBB3B), CircleShape),
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
            Text(text = item.content, color = Color.White, fontSize = 17.sp)
        }
        if (setDivider) ItemDivider()
    }

}

@Composable
private fun ItemDivider() = Divider(
    thickness = 1.dp,
    color = Color(0x42FFFFFF),
    modifier = Modifier.padding(32.dp, end = 32.dp)
)

@Composable
private fun ItemIcon(@DrawableRes icon: Int) = Icon(
    modifier = Modifier.size(24.dp),
    painter = painterResource(id = icon),
    contentDescription = null,
    tint = Color.White
)