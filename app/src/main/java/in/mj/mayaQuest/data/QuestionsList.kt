package `in`.mj.mayaQuest.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = false)
class QuestionsList {
    @field:Json(name = "questions")
    var questionIds: List<Int?> = ArrayList<Int?>()
}