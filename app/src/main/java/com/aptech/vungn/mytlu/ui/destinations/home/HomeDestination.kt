package com.aptech.vungn.mytlu.ui.destinations.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.aptech.vungn.mytlu.R
import com.aptech.vungn.mytlu.data.model.Notification
import com.aptech.vungn.mytlu.data.model.User
import com.aptech.vungn.mytlu.ui.destinations.home.components.*
import com.aptech.vungn.mytlu.ui.destinations.home.vm.HomeViewModel
import com.aptech.vungn.mytlu.ui.theme.MyTluTheme
import com.aptech.vungn.mytlu.util.date.sort.getEarlierNotification
import com.aptech.vungn.mytlu.util.date.sort.getThisWeekNotification
import com.aptech.vungn.mytlu.util.date.sort.getTodayNotification
import com.aptech.vungn.mytlu.util.types.DrawerItemName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*

@Composable
fun HomeDestination(viewModel: HomeViewModel, logout: () -> Unit, onAttending: () -> Unit) {
    val user by viewModel.user.observeAsState()
    val badgeNumber by viewModel.badgeNumber.collectAsState()
    val notifications = viewModel.notifications.collectAsState()
    val isNotificationLoading = viewModel.isNotificationLoading.collectAsState()
    MyTluTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            HomeScreen(
                user = user,
                badgeNumber = badgeNumber,
                notifications = notifications,
                isNotificationLoading = isNotificationLoading,
                onLogout = { logout() },
                onAttending = onAttending,
                onGetNotifications = { viewModel.getNotifications() }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    user: User?,
    badgeNumber: Int,
    notifications: State<List<Notification>>,
    isNotificationLoading: State<Boolean>,
    onLogout: () -> Unit,
    onAttending: () -> Unit,
    onGetNotifications: () -> Unit
) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                DrawerContent(
                    user = user,
                    onClose = { coroutineScope.launch { drawerState.close() } },
                    onItemClick = { name ->
                        coroutineScope.launch {
                            when (name) {
                                DrawerItemName.PROFILE -> {}
                                DrawerItemName.ACADEMIC_RESULT -> {}
                                DrawerItemName.ATTENDANCE_HISTORY -> {}
                                DrawerItemName.DARK_MODE -> {}
                                DrawerItemName.LOGOUT -> {
                                    onLogout()
                                }
                            }
                        }
                    }
                )
            }
        ) {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                Scaffold(
                    modifier = modifier,
                    topBar = {
                        TopBar(coroutineScope, drawerState, pagerState, badgeNumber)
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
                                0 -> Home(onAttending = onAttending)
                                1 -> Notification(
                                    notifications = notifications,
                                    isNotificationLoading = isNotificationLoading,
                                    getNotification = onGetNotifications
                                )
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
    pagerState: PagerState,
    badgeNumber: Int
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
        Tabs(pagerState, coroutineScope, badgeNumber)
    }
}

@Composable
fun Home(modifier: Modifier = Modifier, onAttending: () -> Unit) {
    val isDarkMode = isSystemInDarkTheme()
    Surface(
        modifier = modifier.fillMaxSize(),
        color = if (isDarkMode) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.primary.copy(
            alpha = 0.05f
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            MenuButtonRow(
                icon1 = Icons.Rounded.Newspaper,
                title1 = stringResource(id = R.string.menu_button_newspaper),
                onClick1 = {},
                icon2 = Icons.Rounded.HowToReg,
                title2 = stringResource(id = R.string.menu_button_attendance),
                onClick2 = { onAttending() }
            )
            MenuButtonRow(
                icon1 = Icons.Rounded.Exposure,
                title1 = stringResource(id = R.string.menu_button_newspaper),
                onClick1 = {},
                icon2 = Icons.Rounded.Pages,
                title2 = stringResource(id = R.string.menu_button_attendance),
                onClick2 = {}
            )
            MenuButtonRow(
                icon1 = Icons.Rounded.AcUnit,
                title1 = stringResource(id = R.string.menu_button_newspaper),
                onClick1 = {}
            )
        }
    }
}

@Composable
fun Notification(
    modifier: Modifier = Modifier,
    notifications: State<List<Notification>>,
    isNotificationLoading: State<Boolean>,
    getNotification: () -> Unit
) {
    LaunchedEffect(key1 = true) {
        getNotification()
    }
    Surface(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                modifier = Modifier.padding(
                    start = 20.dp,
                    top = 10.dp,
                    end = 20.dp,
                    bottom = 20.dp
                ),
                text = stringResource(id = R.string.notification_title),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            if (isNotificationLoading.value) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    item {
                        NotificationBox(
                            title = stringResource(id = R.string.today),
                            items = getTodayNotification(notifications.value)
                        )
                    }
                    item {
                        NotificationBox(
                            title = stringResource(id = R.string.this_week),
                            items = getThisWeekNotification(notifications.value)
                        )
                    }
                    item {
                        NotificationBox(
                            title = stringResource(id = R.string.earlier),
                            items = getEarlierNotification(notifications.value)
                        )
                    }
                }
            }

        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewHomeScreen() {
    val isLoading = remember {
        mutableStateOf(false)
    }
    val notifications = remember {
        mutableStateOf(listOf<Notification>())
    }
    MyTluTheme {
        HomeScreen(
            user = User(
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                Date(),
                "",
                "",
                "",
                "",
                ""
            ),
            badgeNumber = 10,
            notifications = notifications,
            isNotificationLoading = isLoading,
            onLogout = {},
            onAttending = {}
        ) {}
    }
}