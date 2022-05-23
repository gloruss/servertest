package features.badge.interactor

import features.badge.entity.BadgeRequest
import features.badge.repository.BadgeRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.*

class GetMonthBadgeInteractor : KoinComponent {

    val badgeRepository : BadgeRepository by inject()

    suspend fun execute(badgeRequest: BadgeRequest) = badgeRepository.getBadgeInMonth(UUID.fromString(badgeRequest.worker_uuid),badgeRequest.time)

}