package features.worker.database

import org.jetbrains.exposed.sql.Table

object WorkerDao : Table("worker") {
    val id = integer("id").primaryKey()
    val name = varchar("name",256)
    val uuid = varchar("uuid",256)
}