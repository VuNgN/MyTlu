package com.aptech.vungn.mytlu.ui.destinations.home.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension

@Composable
fun MenuButtonRow(
    icon1: ImageVector,
    title1: String,
    onClick1: () -> Unit,
    icon2: ImageVector,
    title2: String,
    onClick2: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        val (leftButton, rightButton, spacer) = createRefs()
        MenuButton(
            modifier = Modifier.constrainAs(leftButton) {
                start.linkTo(parent.start)
                end.linkTo(spacer.start)
                height = Dimension.wrapContent
                width = Dimension.fillToConstraints
            },
            icon = icon1,
            title = title1,
            onClick = onClick1
        )
        Spacer(
            modifier = Modifier
                .width(10.dp)
                .constrainAs(spacer) {
                    start.linkTo(leftButton.end)
                    end.linkTo(rightButton.start)
                    width = Dimension.wrapContent
                }
        )
        MenuButton(
            modifier = Modifier.constrainAs(rightButton) {
                start.linkTo(spacer.end)
                end.linkTo(parent.end)
                height = Dimension.wrapContent
                width = Dimension.fillToConstraints
            },
            icon = icon2,
            title = title2,
            onClick = onClick2
        )
    }
}

@Composable
fun MenuButtonRow(icon1: ImageVector, title1: String, onClick1: () -> Unit) {
    MenuButton(
        modifier = Modifier.fillMaxWidth(),
        icon = icon1,
        title = title1,
        onClick = onClick1
    )
}