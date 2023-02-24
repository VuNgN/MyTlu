package com.aptech.vungn.mytlu.ui.destinations.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.HowToReg
import androidx.compose.material.icons.rounded.Newspaper
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.aptech.vungn.mytlu.R
import com.aptech.vungn.mytlu.data.model.Notification
import com.aptech.vungn.mytlu.ui.theme.MyTluTheme
import com.aptech.vungn.mytlu.util.date.formatter.dateToString
import com.aptech.vungn.mytlu.util.types.NotificationStatus
import com.aptech.vungn.mytlu.util.types.NotificationType
import java.util.*

private const val LEFT_LAYOUT = "left_layout"
private const val RIGHT_LAYOUT = "right_layout"

private fun notificationConstraints(): ConstraintSet {
    return ConstraintSet {
        val leftLayout = createRefFor(LEFT_LAYOUT)
        val rightLayout = createRefFor(RIGHT_LAYOUT)

        constrain(leftLayout) {
            start.linkTo(parent.start)
            end.linkTo(rightLayout.start)
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            width = Dimension.fillToConstraints
        }
        constrain(rightLayout) {
            start.linkTo(leftLayout.end)
            end.linkTo(parent.end)
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            width = Dimension.wrapContent
        }
    }
}

@Composable
fun NotificationBox(modifier: Modifier = Modifier, title: String, items: List<Notification>) {
    Surface(modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                modifier = Modifier.padding(start = 20.dp),
                text = title,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                items.forEach { item ->
                    NotificationItem(item = item)
                }
            }
        }
    }
}

@Composable
fun NotificationItem(modifier: Modifier = Modifier, item: Notification) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clickable { },
        color = when (item.status) {
            NotificationStatus.SEEN -> MaterialTheme.colorScheme.background
            NotificationStatus.DELIVERED -> MaterialTheme.colorScheme.primary.copy(
                alpha = 0.05f
            )
        }
    ) {
        BoxWithConstraints(
            modifier = modifier
                .fillMaxWidth()
        ) {
            val constraints = notificationConstraints()
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                constraintSet = constraints
            ) {
                NotificationContent(
                    modifier = Modifier
                        .layoutId(LEFT_LAYOUT)
                        .padding(16.dp),
                    type = item.type,
                    title = item.title,
                    time = item.createOn
                )
                when (item.type) {
                    NotificationType.ATTENDANCE -> if (isCurrentClass()) NotificationAction(
                        modifier = Modifier
                            .layoutId(RIGHT_LAYOUT)
                            .padding(horizontal = 10.dp)
                    )
                    NotificationType.NEWS -> NotificationMedia(
                        modifier = Modifier.layoutId(RIGHT_LAYOUT),
                        url = item.image
                    )
                }
            }
        }
    }
}

fun isCurrentClass(): Boolean {
    return true
}

@Composable
fun NotificationContent(
    modifier: Modifier = Modifier,
    type: NotificationType,
    title: String,
    time: Date
) {
    Row(
        modifier = modifier.wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(modifier = Modifier, contentAlignment = Alignment.Center) {
            Monogram(type = type)
        }
        Column {
            Text(text = title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = dateToString(time, "dd/MM/yyy HH:mm"),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun Monogram(modifier: Modifier = Modifier, type: NotificationType) {
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .size(25.dp),
        color = MaterialTheme.colorScheme.primary
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Icon(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp),
                imageVector = when (type) {
                    NotificationType.ATTENDANCE -> Icons.Rounded.HowToReg
                    NotificationType.NEWS -> Icons.Rounded.Newspaper
                },
                contentDescription = null
            )
        }
    }
}

@Composable
fun NotificationAction(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .wrapContentWidth(),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = { /*TODO*/ }) {
            Text(text = stringResource(id = R.string.attendance_button))
        }
    }
}

@Composable
fun NotificationMedia(modifier: Modifier = Modifier, url: String?) {
    Box(
        modifier = modifier
            .width(80.dp),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = url,
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNotificationBox() {
    MyTluTheme {
        NotificationBox(
            title = "Hôm nay",
            items = listOf(
                Notification(
                    type = NotificationType.ATTENDANCE,
                    title = "Bắn đầu lớp học mới",
                    image = null,
                    createOn = Date(),
                    status = NotificationStatus.DELIVERED
                ),
                Notification(
                    type = NotificationType.NEWS,
                    title = "Nâng cao chất lượng đào tạo sinh viên khối ngành Xây dựng",
                    image = "http://www.tlu.edu.vn/Portals/0/phan-hieu-truong-dai-hoc.jpg",
                    createOn = Date(),
                    status = NotificationStatus.DELIVERED
                ),
                Notification(
                    type = NotificationType.NEWS,
                    title = "Trường Đại học Thủy lợi đứng thứ 5 toàn quốc về tiêu chuẩn dạy học theo kết quả đánh giá xếp hạng của VNUR",
                    image = "http://www.tlu.edu.vn/Portals/0/2023/Thang2/1.png?ver=2023-02-16-232524-863",
                    createOn = Date(),
                    status = NotificationStatus.SEEN
                ),
            )
        )
    }
}
