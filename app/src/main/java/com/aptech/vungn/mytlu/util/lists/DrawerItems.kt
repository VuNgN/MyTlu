package com.aptech.vungn.mytlu.util.lists

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.aptech.vungn.mytlu.MyTluApplication
import com.aptech.vungn.mytlu.R

val drawerBodyItems = listOf(
    DrawerItem(
        DrawerItemName.PROFILE,
        Icons.Rounded.Person,
        MyTluApplication.context.getString(R.string.drawer_item_profile)
    ),
    DrawerItem(
        DrawerItemName.ACADEMIC_RESULT,
        Icons.Rounded.InsertChart,
        MyTluApplication.context.getString(R.string.drawer_item_academic_results)
    ),
    DrawerItem(
        DrawerItemName.ATTENDANCE_HISTORY,
        Icons.Rounded.History,
        MyTluApplication.context.getString(R.string.drawer_item_attendance_history)
    ),
)

val drawerFooterItems = listOf(
    DrawerItem(
        DrawerItemName.DARK_MODE,
        Icons.Rounded.DarkMode,
        MyTluApplication.context.getString(R.string.drawer_item_dark_mode)
    ),
    DrawerItem(
        DrawerItemName.LOGOUT,
        Icons.Rounded.Logout,
        MyTluApplication.context.getString(R.string.drawer_item_logout)
    ),
)

data class DrawerItem(val name: DrawerItemName, val icon: ImageVector, val title: String)

enum class DrawerItemName {
    PROFILE,
    ACADEMIC_RESULT,
    ATTENDANCE_HISTORY,
    DARK_MODE,
    LOGOUT
}