package features.badge.entity

import kotlinx.serialization.Serializable

@Serializable
data class Badge(
    val id  : Int? = null,
    val date : String,
    val start: String? = null,
    val end : String? = null,
    val worker_id : Int,
    val hours : Int = 0,
    val type : String,
    val worker_uuid : String

)
