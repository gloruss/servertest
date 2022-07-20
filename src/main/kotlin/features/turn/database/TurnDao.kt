package features.turn.database

import features.worker.database.WorkerDao.autoIncrement
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date

class TurnDao : IntIdTable("turn") {
    val workerUuid =  varchar("workerUuid",256)
    val date = date("date")
    val startHour = integer("startHour")
    val endHour = integer("endHour")
}