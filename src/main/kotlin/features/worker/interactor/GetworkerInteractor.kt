package features.worker

import features.worker.repository.WorkerRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetWorkerInteractor : KoinComponent{
    val workerRepository : WorkerRepository by inject()
    suspend fun execute() = workerRepository.getWorkers()
}