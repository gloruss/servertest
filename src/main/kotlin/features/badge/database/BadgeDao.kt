package features.badge.database

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.javatime.datetime


object BadgeDao : IntIdTable("badge"){
    val start = datetime("start")
    val end = datetime("end")
    val workerId = integer("worker_id")
    val date = date("date")
    val worker_uuid = uuid("worker_uuid")
    val hours = integer("hours").default(0)
    val type = varchar("type",256)
}