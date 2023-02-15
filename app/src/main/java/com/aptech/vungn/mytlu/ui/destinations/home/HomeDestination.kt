package com.aptech.vungn.mytlu.ui.destinations.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.aptech.vungn.mytlu.ui.destinations.home.vm.HomeViewModel
import com.aptech.vungn.mytlu.ui.theme.MyTluTheme

@Composable
fun HomeDestination(viewModel: HomeViewModel) {
    MyTluTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            HomeScreen()
        }
    }
}

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Text(text = "Hello would")
}