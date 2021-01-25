package `in`.mj.mayaQuest.data

import com.squareup.moshi.Json

//@JsonClass(generateAdapter = true)
data class TopicModel (
    @field:Json(name = "id")   var id:Int?=0,
    @field:Json(name = "Name") var name:String?=""
)