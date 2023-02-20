package com.aptech.vungn.mytlu.ui.destinations.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.aptech.vungn.mytlu.data.model.User
import com.aptech.vungn.mytlu.ui.theme.MyTluTheme
import com.aptech.vungn.mytlu.util.lists.DrawerItemName
import com.aptech.vungn.mytlu.util.lists.drawerBodyItems
import com.aptech.vungn.mytlu.util.lists.drawerFooterItems
import java.util.*

@Composable
fun DrawerContent(
    modifier: Modifier = Modifier,
    user: User?,
    onClose: () -> Unit,
    onItemClick: (DrawerItemName) -> Unit
) {
    ModalDrawerSheet(modifier = modifier.padding(top = 20.dp, bottom = 20.dp, end = 30.dp)) {
        Spacer(Modifier.height(12.dp))
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                DrawerHeading(modifier = Modifier.padding(20.dp), user = user, onClose = onClose)
                Divider(modifier = Modifier.padding(horizontal = 20.dp))
                DrawerBody(onItemClick = onItemClick)
                Divider(modifier = Modifier.padding(horizontal = 20.dp))
                DrawerFooter(onItemClick = onItemClick)
            }
        }
    }
}

@Composable
fun DrawerHeading(
    modifier: Modifier = Modifier,
    user: User?,
    onClose: () -> Unit
) {
    if (user != null) {
        Column(modifier = modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(50)),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = user.avatar,
                        contentDescription = "Avatar profile",
                        contentScale = ContentScale.Crop
                    )
                }
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                    IconButton(onClick = { onClose() }) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = "Back navigation"
                        )
                    }
                }
            }
            Text(
                text = "${user.lastName} ${user.firstName}",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Black
            )
            Text(
                text = user.studentId,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
            )
        }
    }
}

@Composable
private fun DrawerBody(onItemClick: (DrawerItemName) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        drawerBodyItems.forEach { item ->
            DrawerButton(icon = item.icon, title = item.title, onClick = { onItemClick(item.name) })
        }
    }
}

@Composable
fun DrawerFooter(onItemClick: (DrawerItemName) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        drawerFooterItems.forEach { item ->
            DrawerButton(
                icon = item.icon,
                title = item.title,
                onClick = { onItemClick(item.name) }
            )
        }
    }
}

@Composable
fun DrawerButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    title: String,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable { onClick() }, contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(20.dp)
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.padding(end = 16.dp),
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDrawerHeading() {
    MyTluTheme {
        DrawerHeading(
            user = User(
                "Vu",
                "Nguyen Ngoc",
                "",
                "",
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
            ),
            onClose = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDrawerButton() {
    MaterialTheme {
        DrawerButton(icon = Icons.Rounded.Person, title = "Thông tin cá nhân", onClick = {})
    }
}