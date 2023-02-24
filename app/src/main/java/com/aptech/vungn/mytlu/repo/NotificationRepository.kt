package com.aptech.vungn.mytlu.repo

import com.aptech.vungn.mytlu.data.model.Notification
import com.aptech.vungn.mytlu.util.types.NotificationStatus
import com.aptech.vungn.mytlu.util.types.NotificationType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

class NotificationRepository @Inject constructor() {
    suspend fun getNotifications(): List<Notification> {
        return withContext(Dispatchers.IO) {
            val cal = Calendar.getInstance()
            return@withContext listOf(
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
                    image = "https://www.tlu.edu.vn/Portals/0/phan-hieu-truong-dai-hoc.jpg",
                    createOn = cal.also { it.add(Calendar.HOUR, -1) }.time,
                    status = NotificationStatus.DELIVERED
                ),
                Notification(
                    type = NotificationType.NEWS,
                    title = "Trường Đại học Thủy lợi đứng thứ 5 toàn quốc về tiêu chuẩn dạy học theo kết quả đánh giá xếp hạng của VNUR",
                    image = "https://www.tlu.edu.vn/Portals/0/2023/Thang2/1.png?ver=2023-02-16-232524-863",
                    createOn = Date(),
                    status = NotificationStatus.SEEN
                ),
                Notification(
                    type = NotificationType.NEWS,
                    title = "Trường Đại học Thủy lợi đứng thứ 5 toàn quốc về tiêu chuẩn dạy học theo kết quả đánh giá xếp hạng của VNUR",
                    image = "https://www.tlu.edu.vn/Portals/0/2023/Thang2/1.png?ver=2023-02-16-232524-863",
                    createOn = cal.also { it.add(Calendar.DATE, -1) }.time,
                    status = NotificationStatus.SEEN
                ),
                Notification(
                    type = NotificationType.NEWS,
                    title = "Trường Đại học Thủy lợi đứng thứ 5 toàn quốc về tiêu chuẩn dạy học theo kết quả đánh giá xếp hạng của VNUR",
                    image = "https://www.tlu.edu.vn/Portals/0/2023/Thang2/1.png?ver=2023-02-16-232524-863",
                    createOn = cal.also { it.add(Calendar.DATE, -2) }.time,
                    status = NotificationStatus.SEEN
                ),
                Notification(
                    type = NotificationType.NEWS,
                    title = "Trường Đại học Thủy lợi đứng thứ 5 toàn quốc về tiêu chuẩn dạy học theo kết quả đánh giá xếp hạng của VNUR",
                    image = "https://www.tlu.edu.vn/Portals/0/2023/Thang2/1.png?ver=2023-02-16-232524-863",
                    createOn = cal.also { it.add(Calendar.DATE, -4) }.time,
                    status = NotificationStatus.SEEN
                ),
                Notification(
                    type = NotificationType.NEWS,
                    title = "Trường Đại học Thủy lợi đứng thứ 5 toàn quốc về tiêu chuẩn dạy học theo kết quả đánh giá xếp hạng của VNUR",
                    image = "https://www.tlu.edu.vn/Portals/0/2023/Thang2/1.png?ver=2023-02-16-232524-863",
                    createOn = cal.also { it.add(Calendar.DATE, -6) }.time,
                    status = NotificationStatus.SEEN
                )
            )
        }
    }
}