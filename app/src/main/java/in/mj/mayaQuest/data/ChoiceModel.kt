package `in`.mj.mayaQuest.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/*
{
"answer":0,
"choice":"Image assets for a high traffic website\n","id":1516,
"choice_type":0,
"q_id":2
}
 */
@JsonClass(generateAdapter = true)
class ChoiceModel {
    @field:Json(name = "answer") var answer:Int?=0
    @field:Json(name = "choice") var choice:String?=""
    @field:Json(name = "choice_type") var choiceType:Int?=0
    @field:Json(name = "q_id") var questionId:Int?=0
}