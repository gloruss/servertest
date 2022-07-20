package features.turn.entity

data class Turn(
    val id : Int,
    val workerUUid : String,
    val startHour : Int,
    val endHour : Int,
    val date : String
)
