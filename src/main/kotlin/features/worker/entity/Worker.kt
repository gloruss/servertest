package features.worker.entity

import kotlinx.serialization.Serializable

@Serializable
data class Worker(
    val id : Int? = null,
    val name : String,
    val uuid : String
)