package `in`.mj.mayaQuest.network


//import `in`.mj.mayaQuest.data.TopicList
import `in`.mj.mayaQuest.data.QuestionModel
import `in`.mj.mayaQuest.data.QuestionsList
import `in`.mj.mayaQuest.data.TopicModel
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


internal interface APIInterface {
    @GET("/get_topics")
    fun getTopicsAsync(): Deferred<Response<List<TopicModel>>>

    @GET("/start_quest:{topicID}")
    fun startQuestAsync(@Path("topicID") topicID: Int?): Deferred<Response<QuestionsList>>

    @GET("/get_quest:{questionId}")
    fun getQuestionDetailsAsync(@Path("questionId") questionId: Int?): Deferred<Response<QuestionModel>>
//    @GET("/api/unknown")
//    fun doGetListResources(): Call<MultipleResource?>?

//    @POST("/api/users")
//    fun createUser(@Body user: User?): Call<User?>?

//    @GET("/api/users?")
//    fun doGetUserList(@Query("page") page: String?): Call<UserList?>?

//    @FormUrlEncoded
//    @POST("/api/users?")
//    fun doCreateUserWithField(
//        @Field("name") name: String?,
//        @Field("job") job: String?
//    ): Call<UserList?>?
}