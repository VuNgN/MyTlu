package com.aptech.vungn.mytlu.ui.destinations.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import com.aptech.vungn.mytlu.data.model.User
import com.aptech.vungn.mytlu.ui.destinations.home.components.DrawerContent
import com.aptech.vungn.mytlu.ui.destinations.home.components.MyTluLogo
import com.aptech.vungn.mytlu.ui.destinations.home.components.Tabs
import com.aptech.vungn.mytlu.ui.destinations.home.vm.HomeViewModel
import com.aptech.vungn.mytlu.ui.theme.MyTluTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*

@Composable
fun HomeDestination(viewModel: HomeViewModel) {
    val user by viewModel.user.observeAsState()
    MyTluTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            HomeScreen(user = user)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier, user: User?) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                DrawerContent(
                    user = user,
                    onClose = { coroutineScope.launch { drawerState.close() } }
                )
            }
        ) {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                Scaffold(
                    modifier = modifier,
                    topBar = {
                        TopBar(coroutineScope, drawerState, pagerState)
                    },
                ) { paddingValues ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                    ) {
                        HorizontalPager(
                            modifier = Modifier.fillMaxSize(),
                            pageCount = 2,
                            state = pagerState
                        ) { page ->
                            when (page) {
                                0 -> Home()
                                1 -> Notification()
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
private fun TopBar(
    coroutineScope: CoroutineScope,
    drawerState: DrawerState,
    pagerState: PagerState
) {
    Column(modifier = Modifier) {
        TopAppBar(modifier = Modifier, title = {
            MyTluLogo()
        }, actions = {
            IconButton(onClick = { coroutineScope.launch { drawerState.open() } }) {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "Open navigation drawer"
                )
            }
        })
        Tabs(pagerState, coroutineScope)
    }
}

@Composable
fun Notification(modifier: Modifier = Modifier) {
    Surface(modifier = modifier.fillMaxSize()) {}
}

@Composable
fun Home(modifier: Modifier = Modifier) {
    Surface(modifier = modifier.fillMaxSize()) {}
}

@Preview(showSystemUi = true)
@Composable
fun PreviewHomeScreen() {
    MyTluTheme {
        HomeScreen(
            user = User(
                "Vu",
                "Nguyen Ngoc",
                "",
                "https://static.vecteezy.com/system/resources/previews/002/275/847/original/male-avatar-profile-icon-of-smiling-caucasian-man-vector.jpg",
                "",
                "1951061127",
                "",
                "",
                "",
                Date(),
                "",
                "",
                "",
                "",
                ""
            )
        )
    }
}