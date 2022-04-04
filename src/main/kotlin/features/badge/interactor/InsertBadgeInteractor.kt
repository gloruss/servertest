package features.badge.interactor

import features.badge.entity.Badge
import features.badge.repository.BadgeRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class InsertBadgeInteractor : KoinComponent {

    val badgeRepository : BadgeRepository by inject()

    suspend fun execute(badge: Badge) = badgeRepository.insertBadge(badge)

}