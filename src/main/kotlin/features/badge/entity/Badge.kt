package features.badge.entity

import kotlinx.serialization.Serializable

@Serializable
data class Badge(
    val id  : Int? = null,
    val start: String? = null,
    val end : String? = null,
    val worker_id : Int?,
    val hours : Int = 0,
    val type : String,
    val worker_uuid : String

)
