package features.badge.interactor

import features.badge.entity.BadgeRequest
import features.badge.repository.BadgeRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class EndBadgeInteractor : KoinComponent {

    val badgeRepository : BadgeRepository by inject()

    suspend fun execute(badgeRequest: BadgeRequest) = badgeRepository.modifyBadge(badgeRequest)
}