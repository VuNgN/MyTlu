package com.aptech.vungn.mytlu.ui.destinations.login.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import com.aptech.vungn.mytlu.R

@Composable
fun TopBar(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.height(64.dp)
    ) {
        val isDarkMode = isSystemInDarkTheme()
        val logo = if (isDarkMode) ImageBitmap.imageResource(id = R.drawable.mytlulogolight)
        else ImageBitmap.imageResource(id = R.drawable.mytlulogo)
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Image(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                bitmap = logo,
                contentDescription = "App's logo"
            )
        }
    }
}