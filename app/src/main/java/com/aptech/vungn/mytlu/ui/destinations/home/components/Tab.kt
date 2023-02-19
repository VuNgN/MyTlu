package com.aptech.vungn.mytlu.ui.destinations.home.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.aptech.vungn.mytlu.util.lists.TabName
import com.aptech.vungn.mytlu.util.lists.tabList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Tabs(pagerState: PagerState, coroutineScope: CoroutineScope, badgeNumber: Int) {
    val tabWidths = remember {
        val tabWidthStateList = mutableStateListOf<Dp>()
        repeat(tabList.size) {
            tabWidthStateList.add(24.dp)
        }
        tabWidthStateList
    }
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                modifier = Modifier.customTabIndicatorOffset(
                    currentTabPosition = tabPositions[pagerState.currentPage],
                    tabWidth = tabWidths[pagerState.currentPage]
                ), color = Color.Transparent
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
        tabs = {
            tabList.forEachIndexed { index, item ->
                val isSelected = pagerState.currentPage == index
                Tab(
                    icon = {
                        if (item.name == TabName.NOTIFICATION) {
                            BadgedBox(
                                badge = {
                                    Badge(modifier = Modifier.offset(x = (-8).dp, y = 8.dp)) {
                                        Text(text = "$badgeNumber")
                                    }
                                }) {
                                Icon(
                                    imageVector = if (isSelected) item.selectedIcon else item.unselectIcon,
                                    contentDescription = item.title,
                                )
                            }
                        } else {
                            Icon(
                                imageVector = if (isSelected) item.selectedIcon else item.unselectIcon,
                                contentDescription = item.title,
                            )
                        }
                    },
                    selected = isSelected,
                    onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
                    selectedContentColor = MaterialTheme.colorScheme.primary,
                    unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    )
}

fun Modifier.customTabIndicatorOffset(
    currentTabPosition: TabPosition,
    tabWidth: Dp
): Modifier = composed(
    inspectorInfo = debugInspectorInfo {
        name = "customTabIndicatorOffset"
        value = currentTabPosition
    }
) {
    val currentTabWidth by animateDpAsState(
        targetValue = tabWidth,
        animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing)
    )
    val indicatorOffset by animateDpAsState(
        targetValue = ((currentTabPosition.left + currentTabPosition.right - tabWidth) / 2),
        animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing)
    )
    fillMaxWidth()
        .wrapContentSize(Alignment.BottomStart)
        .offset(x = indicatorOffset)
        .width(currentTabWidth)
        .border(
            shape = RoundedCornerShape(topStart = 100.dp, topEnd = 100.dp),
            color = MaterialTheme.colorScheme.primary,
            width = currentTabWidth
        )
}