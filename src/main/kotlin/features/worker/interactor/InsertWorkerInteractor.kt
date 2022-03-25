package features.worker.interactor

import features.worker.entity.Worker
import features.worker.repository.WorkerRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class InsertWorkerInteractor : KoinComponent{
    val workerRepository : WorkerRepository by inject()

    suspend fun execute(worker: Worker) = workerRepository.put(worker)
}