import features.badge.repository.BadgeRepository
import features.worker.repository.WorkerRepository
import org.koin.dsl.module

val workerModule = module {
    single { WorkerRepository() }
    single { BadgeRepository() }
}