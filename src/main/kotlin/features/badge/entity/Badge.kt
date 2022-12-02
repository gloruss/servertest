package features.badge.entity

import kotlinx.serialization.Serializable

@Serializable
data class Badge(
    val id  : Int,
    val start: String? = null,
    val end : String? = null,
    val worker_id : Int?,
    val workedMinutes : Int? = null,
    val type : String,
    val worker_uuid : String

)
