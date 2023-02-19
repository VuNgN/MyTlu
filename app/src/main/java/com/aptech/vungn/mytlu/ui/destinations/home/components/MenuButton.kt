package com.aptech.vungn.mytlu.ui.destinations.home.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Newspaper
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aptech.vungn.mytlu.ui.theme.MyTluTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    title: String,
    onClick: () -> Unit
) {
    val darkMode = isSystemInDarkTheme()
    ElevatedCard(
        modifier = modifier,
        onClick = onClick,
        colors = if (darkMode) CardDefaults.elevatedCardColors()
        else CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.onPrimary),
        elevation = if (darkMode) CardDefaults.elevatedCardElevation()
        else CardDefaults.elevatedCardElevation(defaultElevation = 5.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            TopIcon(icon = icon)
            Title(title = title)
        }
    }
}

@Composable
fun Title(modifier: Modifier = Modifier, title: String) {
    Text(modifier = modifier, text = title, style = MaterialTheme.typography.titleMedium)
}

@Composable
fun TopIcon(modifier: Modifier = Modifier, icon: ImageVector) {
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .size(50.dp),
        color = MaterialTheme.colorScheme.primary
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Icon(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                imageVector = icon,
                contentDescription = null
            )
        }
    }
}

@Preview
@Composable
fun PreviewMenuButton() {
    MyTluTheme {
        MenuButton(icon = Icons.Rounded.Newspaper, title = "Tin tức", onClick = {})
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun PreviewMenuButtonDarkMode() {
    MyTluTheme {
        MenuButton(icon = Icons.Rounded.Newspaper, title = "Tin tức", onClick = {})
    }
}
