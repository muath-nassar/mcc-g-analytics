package com.example.googleanalytics

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.googleanalytics.databinding.ActivityMainBinding
import com.example.googleanalytics.models.TrackedActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity(),TrackedActivity {
    private lateinit var mIntent: Intent
    private val TAG = "mmm"
    private var timer: Int = 0
    private lateinit var firestore: FirebaseFirestore
    private lateinit var thread: Thread
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    private  lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firestore = Firebase.firestore
        firebaseAnalytics = Firebase.analytics
        addGoogleAnalyticsTrackEvent()

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
        binding.btnClothes.setOnClickListener {
            mIntent = Intent(this,ClothesCategoryActivity::class.java)
            addLogSelectItem("clothes")
            startActivity(mIntent)
        }
        binding.btnFood.setOnClickListener {
            mIntent = Intent(this,FoodCategoryActivity::class.java)
            addLogSelectItem("food")
            startActivity(mIntent)
        }
        binding.btnPhones.setOnClickListener {
            mIntent = Intent(this,PhonesCategoryActivity::class.java)
            addLogSelectItem("phones")
            startActivity(mIntent)
        }
    }
    override fun addGoogleAnalyticsTrackEvent() {
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
            param(FirebaseAnalytics.Param.SCREEN_NAME, "Categories")
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
}