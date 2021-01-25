package `in`.mj.mayaQuest.data

import com.squareup.moshi.Json
import java.util.*


/*
id = Column(Integer, primary_key=True)
question = Column(String)
choices = relationship("Choices")
topic_id = Column(Integer, ForeignKey('Topics.id'))
{
    "question":"Which of the following tasks would Nearline Storage be well suited for?",
    "id":2,
    "topic_id":1,
    "choices":[
        {
            "answer":0,
            "choice": "A mounted Linux file system\n","id":1515,
            "choice_type":0,
            "q_id":2
        },
        {
            "answer":0,
            "choice":"Image assets for a high traffic website\n","id":1516,
            "choice_type":0,
            "q_id":2
        }
    ]
}
 */
//@JsonClass(generateAdapter = false)
//data class QuestionModel (
//    @field:Json(name = "id")  var id:Int,
//    @field:Json(name = "question")  var question:String,
//    @field:Json(name = "choices")  var choices: ArrayList<ChoiceModel> ,
//    @field:Json(name = "topic_id")  var topicId:Int
//)

class QuestionModel{
    @Json(name = "question")
    private var question: String? = null

    @Json(name = "id")
    private var id: Int? = null

    @Json(name = "topic_id")
    private var topicId: Int? = null

    @Json(name = "choices")
    private var choices: List<Choice> = ArrayList<Choice>()

    fun getQuestion(): String? {
        return question.toString()
    }

    fun setQuestion(question: String?) {
        this.question = question
    }

    fun getId(): Int? {
        return id
    }

    fun setId(id: Int?) {
        this.id = id
    }

    fun getTopicId(): Int? {
        return topicId
    }

    fun setTopicId(topicId: Int?) {
        this.topicId = topicId
    }

    fun getChoices(): List<Choice>? {
        return choices
    }

    fun setChoices(choices: List<Choice>) {
        this.choices = choices
    }

}
class Choice {
    @Json(name = "answer")
    var answer: Int? = null

    @Json(name = "choice")
    var choice: String? = null

    @Json(name = "id")
    var id: Int? = null

    @Json(name = "choice_type")
    var choiceType: Int? = null

    @Json(name = "q_id")
    var qId: Int? = null
}