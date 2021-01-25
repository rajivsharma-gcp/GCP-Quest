package `in`.mj.mayaQuest.viewModel
//
//import `in`.mj.mayaQuest.data.HomeRepository
//import `in`.mj.mayaQuest.data.TopicModel
//import android.app.Application
//import androidx.lifecycle.AndroidViewModel
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//
//class HomeViewModel(application: Application): AndroidViewModel(application){
//
//private var homeRepository: HomeRepository?=null
//        var TopicModelListLiveData : LiveData<List<TopicModel>>?=null
//        var createPostLiveData:LiveData<TopicModel>?=null
//        var deletePostLiveData:LiveData<Boolean>?=null
//
//        init {
//        homeRepository = HomeRepository()
//        TopicModelListLiveData = MutableLiveData()
//        createPostLiveData = MutableLiveData()
//        deletePostLiveData = MutableLiveData()
//        }
//
//        fun fetchAllTopics(){
//                TopicModelListLiveData = homeRepository?.fetchAllTopics()
//        }
//
////        fun createPost(TopicModel: TopicModel){
////        createPostLiveData = homeRepository?.createPost(TopicModel)
////        }
////
////        fun deletePost(id:Int){
////        deletePostLiveData = homeRepository?.deletePost(id)
////        }
//
//        }