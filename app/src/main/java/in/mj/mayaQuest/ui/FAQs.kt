package `in`.mj.mayaQuest.ui

import `in`.mj.mayaQuest.R
import `in`.mj.mayaQuest.const.MayaQuestConstants
import `in`.mj.mayaQuest.network.APIClient
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*


class FAQs : AppCompatActivity() {
    private var currentQuestion = 0
//    var questProgress = HashMap<Int, ArrayList<Int>>()
    private lateinit var questionList: ArrayList<Int>
    private fun populateView() {
        val myView = this

        lifecycleScope.launch {

            try {
                val webResponse =
                    APIClient.getClient.getQuestionDetailsAsync(questionList[currentQuestion])
                        .await()
                Timber.d("Response: %s", webResponse.body().toString())


                if (webResponse.isSuccessful) {
                    Timber.d("FAQ - response good")
                    val questionDetails = webResponse.body()
                    Timber.d("QuestionDetails %s", questionDetails.toString())
                    val question = myView.findViewById<TextView>(R.id.question)
                    if (questionDetails != null) {
                        val quest = questionDetails.getQuestion().toString()
                        Timber.d("quest: $quest")
                        question.text = quest
                        val llChoices = myView.findViewById<LinearLayout>(R.id.llChoices)
                        llChoices.removeAllViews()
                        var index = 0
                        for (choice in questionDetails.getChoices()!!) {
                            val cbDynamicChoice = CheckBox(myView)
                            cbDynamicChoice.text = choice.choice
                            cbDynamicChoice.tag = choice.answer
                            cbDynamicChoice.setBackgroundColor(
                                Color.parseColor(
                                    MayaQuestConstants.COLORS[index++]
                                )
                            )
                            setMargins(cbDynamicChoice, 5,15,5,15)

                            llChoices.addView(cbDynamicChoice)
                        }
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(
                    this@FAQs,
                    "Network Issue: $e",
                    Toast.LENGTH_SHORT
                )
                    .show()
                Timber.e(e)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faqs)
        questionList = intent.getIntegerArrayListExtra("questionList")!!
        Timber.d("questionList: %s", questionList.toString())

        // Lets populate the Current Question
        val btNext: Button = this.findViewById(R.id.btNext)
        // set on-click listener
        btNext.setOnClickListener {
            if (currentQuestion < questionList.size - 1) {
                currentQuestion += 1
            } else {
                Toast.makeText(this@FAQs, "Reached the last question", Toast.LENGTH_SHORT).show()
            }
            this.populateView()
        }

        val btPrevious: Button = this.findViewById(R.id.btPrevious)
        // set on-click listener
        btPrevious.setOnClickListener {
            if (currentQuestion > 0) {
                currentQuestion -= 1
            } else {
                Toast.makeText(this@FAQs, "Already on the first question", Toast.LENGTH_SHORT)
                    .show()
            }
            this.populateView()
        }
        val btValidate: Button = this.findViewById(R.id.btValidate)
        // set on-click listener
        btValidate.setOnClickListener {
            val llChoices = this.findViewById<LinearLayout>(R.id.llChoices)
            var passedFlg = true
            for (i in 0 until llChoices.childCount) {
                val choice: CheckBox = llChoices.getChildAt(i) as CheckBox

                if ((choice.tag == 1) != choice.isChecked) {
                    passedFlg = false
                    break
                }
            }
            if (passedFlg) {
                Toast.makeText(this@FAQs, "Good Job", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@FAQs, "Sorry Wrong Answers selected", Toast.LENGTH_SHORT).show()
            }
        }


        this.populateView()
    }

    private fun setMargins(view: View, left: Int, top: Int, right: Int, bottom: Int) {
        if (view.layoutParams is MarginLayoutParams) {
            val p = view.layoutParams as MarginLayoutParams
            p.setMargins(left, top, right, bottom)
            view.requestLayout()
        }
    }
}