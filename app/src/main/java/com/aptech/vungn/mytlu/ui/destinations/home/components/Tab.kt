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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.aptech.vungn.mytlu.util.lists.tabList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Tabs(pagerState: PagerState, coroutineScope: CoroutineScope) {
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
                        Icon(
                            imageVector = if (isSelected) item.selectedIcon else item.unselectIcon,
                            contentDescription = item.titleResource,
                        )
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