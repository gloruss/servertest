package features.badge.database

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.datetime

object BadgeTime : IntIdTable("badge_time") {
    val time = datetime("time")
    val worker_uuid = uuid("worker_uuid")
    val isEnter = bool("enter")
}