package `in`.mj.mayaQuest.ui

import `in`.mj.mayaQuest.BuildConfig
import `in`.mj.mayaQuest.R
import `in`.mj.mayaQuest.data.QuestionsList
import `in`.mj.mayaQuest.data.TopicModel
import `in`.mj.mayaQuest.network.APIClient
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*


class MainActivity : AppCompatActivity() {
    private val listOfItems: MutableList<String> = ArrayList() // Creating an empty arraylist
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var allTopics: Spinner
    private val tag = "Main Activity"
    private val topicsAndIds = HashMap<String, Int>()
    lateinit var mGoogleSignInClient: GoogleSignInClient

    private val auth by lazy {
        FirebaseAuth.getInstance()
    }

    private fun loadTopicList() {
        listOfItems.add(0, "Please wait, loading topics ...")

        findViewById<Spinner>(R.id.all_topics).also { allTopics = it }
        adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listOfItems)
        allTopics.adapter = adapter
        // Launch Kotlin Coroutine on Android's main thread
        GlobalScope.launch(Dispatchers.Main) {
            // Execute web request through coroutine call adapter & retrofit
            try {
                val webResponse = APIClient.getClient.getTopicsAsync().await()

                if (webResponse.isSuccessful) {
                    val topicList: List<TopicModel>? = webResponse.body()

                    //Timber.d(tag, topicList.toString())
                    if (topicList != null) {
                        listOfItems.removeAt(0)
                        for (topic in topicList) {
                            val name = topic.name.toString()
                            topicsAndIds.put(name, topic.id!!)
                            listOfItems.add(name)
                            Timber.d(topic.name.toString())
                        }
                        allTopics.adapter = adapter
                    }
                }
            }   catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Network Issue: $e", Toast.LENGTH_SHORT).show()
                Timber.e(e)
            }
        }
    }

    private fun getQuestionIds() {
        // Launch Kotlin Coroutine on Android's main thread
        GlobalScope.launch(Dispatchers.Main) {
            // Execute web request through coroutine call adapter & retrofit
            val selectedTopic = topicsAndIds[allTopics.selectedItem.toString()]
            Timber.d("selectedTopic: %s", selectedTopic)
            try{
            val webResponse = APIClient.getClient.startQuestAsync(selectedTopic).await()
            Timber.d("Response: %s", webResponse.body().toString())
            if (webResponse.isSuccessful) {
                val questionList: QuestionsList? = webResponse.body()

                if (questionList != null) {
                    Timber.d(questionList.questionIds.toString())
                }
                if (questionList != null) {
                    // Lets get id from topic name
                    val questIds = ArrayList<Int?>(questionList.questionIds)
                    Timber.d("questIds: %s", questIds)

                    // Lets get all the question_ids, which will be used for the test.
                    val intent = Intent(this@MainActivity, FAQs::class.java)
                    intent.putIntegerArrayListExtra("questionList", questIds)
                    val bStartTest: Button = this@MainActivity.findViewById(R.id.bStartTest)
                    bStartTest.isEnabled = true
                    bStartTest.isClickable = true
                    startActivity(intent)
                }
            }
            }   catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Network Issue: $e", Toast.LENGTH_SHORT).show()
                Timber.e(e)
            }
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Timber.d("Inside onCreate")
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        loadTopicList()

        val bStartTest: Button = this.findViewById(R.id.bStartTest)
//        bStartTest.isEnabled = true
//        bStartTest.isClickable = true
        // set on-click listener
        bStartTest.setOnClickListener {
            bStartTest.isEnabled = false
            bStartTest.isClickable = false
            getQuestionIds()
//            bStartTest.isEnabled = true
//            bStartTest.isClickable = true
        }



        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient= GoogleSignIn.getClient(this,gso)

        val logout: Button = this.findViewById(R.id.logout)
        logout.setOnClickListener {
            mGoogleSignInClient.signOut().addOnCompleteListener {
                val intent= Intent(this, LoginScreen::class.java)
               // intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
        }


    }


/*
    fun bLogout(view:View) {
        Firebase.auth.signOut()
        finish()
        val logoutIntent = Intent(this, Login::class.java)
        //logoutIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(logoutIntent)
       // finish()
   }

*/

//
//    override fun onPostResume() {
//        super.onPostResume()
//                Timber.d("Inside onPostResume")
//        setContentView(R.layout.activity_main)
//        val bStartTest: Button = this.findViewById(R.id.bStartTest)
//        loadTopicList()
//        bStartTest.isEnabled = true
//        bStartTest.isClickable = true
//    }
//
//    override  fun onResume() {
//        super.onResume()
//        Timber.d("Inside onResume")
//        setContentView(R.layout.activity_main)
//        val bStartTest: Button = this.findViewById(R.id.bStartTest)
//        loadTopicList()
//        bStartTest.isEnabled = true
//        bStartTest.isClickable = true
//
//    }
}

