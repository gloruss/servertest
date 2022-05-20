package features.badge.database

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.javatime.datetime
import org.jetbrains.exposed.sql.javatime.time


object BadgeDao : IntIdTable("badge"){
    val start = datetime("start")
    val end = datetime("end")
    val workerId = integer("worker_id")
    val worker_uuid = uuid("worker_uuid")
    val hours = time("hours")
    val type = varchar("type",256)
}