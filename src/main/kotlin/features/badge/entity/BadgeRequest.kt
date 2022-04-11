package features.badge.entity

import kotlinx.serialization.Serializable

@Serializable
data class BadgeRequest (
    val time : String,
    val worker_uuid : String,
    val date : String
)