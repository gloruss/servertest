package features.worker.repository

import features.auth.database.UserAccountsDao
import features.auth.entity.UserAccount
import features.worker.database.WorkerDao
import features.worker.entity.Worker
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import util.dbQuery
import java.util.*

class WorkerRepository {




    suspend fun getWorkerById(id : Int) = dbQuery{
        WorkerDao.select { WorkerDao.id eq id }
            .map { toWorker(it) }
            .singleOrNull()
    }

    suspend fun put(worker: Worker) = dbQuery {
        WorkerDao.insert {

            it[name] = worker.name
            it[uuid] = UUID.randomUUID().toString()
        }.resultedValues?.map {
            toWorker(it)
        }?.singleOrNull()
    }

    suspend fun getWorkers() = dbQuery {
        WorkerDao.selectAll()
            .map { toWorker(it) }
    }

    private fun toWorker(row: ResultRow) = Worker(
        id = row[WorkerDao.id],
        name = row[WorkerDao.name],
        uuid = row[WorkerDao.uuid]
    )
}