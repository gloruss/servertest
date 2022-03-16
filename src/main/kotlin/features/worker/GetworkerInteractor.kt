package features.worker

import features.worker.repository.WorkerRepository

class GetWorkerInteractor(
    val workerRepository: WorkerRepository = WorkerRepository()
) {
    suspend fun execute() = workerRepository.getWorkers()
}