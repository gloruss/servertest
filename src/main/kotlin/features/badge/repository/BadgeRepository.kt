package features.badge.repository

import features.badge.database.BadgeDao
import features.badge.entity.Badge
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormatter

import util.dbQuery
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter.*
import java.util.*


class BadgeRepository {

    val simpletimeFormat = SimpleDateFormat("HH:mm")
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")

    suspend fun insertBadge(badge: Badge)= dbQuery {
        BadgeDao.insert {
            it[start] = DateTime.parse(badge.start)
            it[date] = DateTime.parse(badge.date)
            it[end] = DateTime.parse(badge.end)
            it[workerId] = badge.worker_id
            it[worker_uuid] = UUID.fromString(badge.worker_uuid)
            it[hours] = badge.hours
            it[type] = badge.type
        }.resultedValues?.map {
            toBadge(it)
        }?.singleOrNull()
    }


    private fun toBadge(row: ResultRow) : Badge = Badge(
        id = row[BadgeDao.id],
        start = row[BadgeDao.start].toString(),
        end = row[BadgeDao.end].toString(),
        date = simpleDateFormat.format(row[BadgeDao.date]),
        worker_id = row[BadgeDao.workerId],
        worker_uuid = row[BadgeDao.worker_uuid].toString(),
        hours = row[BadgeDao.hours],
        type = row[BadgeDao.type]
    )
}