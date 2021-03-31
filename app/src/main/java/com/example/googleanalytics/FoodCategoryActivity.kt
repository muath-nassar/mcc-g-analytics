package com.example.googleanalytics

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.googleanalytics.databinding.ActivityFoodBinding
import com.example.googleanalytics.models.Product
import com.example.googleanalytics.models.TrackedActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FoodCategoryActivity : AppCompatActivity(),TrackedActivity {
    private val data = mutableListOf<Product>()
    private lateinit var mIntent: Intent
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    private lateinit var firestore: FirebaseFirestore
    private var timer = 0
    private lateinit var thread: Thread
    lateinit var binding: ActivityFoodBinding
    private val TAG = "mmm"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAnalytics = Firebase.analytics
        firestore = Firebase.firestore
        addGoogleAnalyticsTrackEvent()
        getFoodData()

    }

    override fun addGoogleAnalyticsTrackEvent() {
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
            param(FirebaseAnalytics.Param.SCREEN_NAME, "Food")
            param(FirebaseAnalytics.Param.SCREEN_CLASS, localClassName)
        }
    }
    override fun onStartTimer() {
        thread = Thread{
            ++timer
            Thread.sleep(1000)
        }
        thread.start()
    }

    override fun onStopTimer() {
        thread.interrupt()
        val userId = ApplicationHelper().userId
        val screenName = localClassName
        firestore.collection("Screen Logs").add(
            mutableMapOf(
                "userId" to userId,
                "screenName" to screenName,
                "time duration" to timer
            )
        ).addOnSuccessListener {
            Log.d(TAG,"added logs to firestore succeed ")
        }.addOnFailureListener {
            Log.d(TAG,"added failed .  logs to firestore failed ")
        }

    }
    private fun addLogSelectItem(name: String){
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM) {
            param(FirebaseAnalytics.Param.ITEM_NAME, name)
            param(FirebaseAnalytics.Param.CONTENT_TYPE, "category")
        }
    }
    override fun onStart() {
        super.onStart()
        onStartTimer()
    }

    override fun onStop() {
        super.onStop()
        onStopTimer()
    }

    override fun onResume() {
        super.onResume()
        binding.btnMeat.setOnClickListener {
            mIntent = Intent(this, ProductActivity::class.java)
            addLogSelectItem("Meat")
            data.forEach {
                if (it.name == "Meat"){
                    mIntent.putExtra("product", it)
                    startActivity(mIntent)
                }
            }
        }
        binding.btnCake.setOnClickListener {
            mIntent = Intent(this, ProductActivity::class.java)
            addLogSelectItem("Cake")
            data.forEach {
                if (it.name == "Cake"){
                    mIntent.putExtra("product", it)
                    startActivity(mIntent)
                }
            }
            Log.d(TAG, "phone selected")
        }
    }
    private fun getFoodData() {
        firestore.collection("foods").get()
            .addOnSuccessListener { snapshot ->
                for (document in snapshot){
                    data.add(
                        Product(
                            document.getString("name")!!,
                            document.getString("description")!!,
                            document.getString("img")!!
                        )
                    )
                }
            }
            .addOnFailureListener { Toast.makeText(this,"Failure", Toast.LENGTH_SHORT).show() }
    }
}