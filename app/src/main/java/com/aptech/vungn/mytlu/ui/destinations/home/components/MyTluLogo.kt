package com.aptech.vungn.mytlu.ui.destinations.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import com.aptech.vungn.mytlu.R

@Composable
fun MyTluLogo(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.height(64.dp)
    ) {
        val isDarkMode = isSystemInDarkTheme()
        val logo =
            if (isDarkMode) ImageBitmap.imageResource(id = R.drawable.mytlulogolight)
            else ImageBitmap.imageResource(id = R.drawable.mytlulogo)

        Image(
            modifier = Modifier
                .fillMaxHeight()
                .padding(vertical = 10.dp),
            bitmap = logo,
            contentScale = ContentScale.Crop,
            contentDescription = "App's logo",
        )
    }
}