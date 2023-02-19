package com.aptech.vungn.mytlu.util.lists

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.ui.graphics.vector.ImageVector
import com.aptech.vungn.mytlu.MyTluApplication
import com.aptech.vungn.mytlu.R

val tabList = listOf(
    Tab(
        TabName.HOME,
        Icons.Outlined.Home,
        Icons.Rounded.Home,
        MyTluApplication.context.getString(R.string.home_tab)
    ),
    Tab(
        TabName.NOTIFICATION,
        Icons.Outlined.Notifications,
        Icons.Rounded.Notifications,
        MyTluApplication.context.getString(R.string.notification_tab)
    )
)

data class Tab(
    val name: TabName,
    val unselectIcon: ImageVector,
    val selectedIcon: ImageVector,
    val title: String
)

enum class TabName {
    HOME,
    NOTIFICATION
}