package features.badge.database

import org.jetbrains.exposed.sql.Table

object BadgeDao : Table("badge"){
    val id = integer("id").primaryKey().autoIncrement()
    val start = datetime("start")
    val end = datetime("end")
    val workerId = integer("worker_id")
    val date = date("date")
    val worker_uuid = uuid("worker_uuid")
    val hours = integer("worker_hour").default(0)
    val type = varchar("type",256)
}