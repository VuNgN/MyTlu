package com.aptech.vungn.mytlu.util.lists

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.aptech.vungn.mytlu.MyTluApplication
import com.aptech.vungn.mytlu.R

val drawerBodyItems = listOf(
    DrawerItem(
        Icons.Rounded.Person,
        MyTluApplication.context.getString(R.string.drawer_item_profile)
    ),
    DrawerItem(
        Icons.Rounded.InsertChart,
        MyTluApplication.context.getString(R.string.drawer_item_academic_results)
    ),
    DrawerItem(
        Icons.Rounded.History,
        MyTluApplication.context.getString(R.string.drawer_item_attendance_history)
    ),
)

val drawerFooterItems = listOf(
    DrawerItem(
        Icons.Rounded.DarkMode,
        MyTluApplication.context.getString(R.string.drawer_item_dark_mode)
    ),
    DrawerItem(
        Icons.Rounded.Logout,
        MyTluApplication.context.getString(R.string.drawer_item_logout)
    ),
)

data class DrawerItem(val icon: ImageVector, val title: String)