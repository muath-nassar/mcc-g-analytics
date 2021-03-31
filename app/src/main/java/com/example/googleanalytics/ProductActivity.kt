package com.example.googleanalytics

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.example.googleanalytics.databinding.ActivityProductBinding
import com.example.googleanalytics.models.Product
import com.example.googleanalytics.models.TrackedActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ProductActivity : AppCompatActivity(),TrackedActivity {
    private lateinit var product: Product
    private lateinit var firestore: FirebaseFirestore
    private var timer = 0
    private lateinit var thread: Thread
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    private val TAG = "mmm"
    private lateinit var binding : ActivityProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        product = intent.getParcelableExtra("product")!!
        firebaseAnalytics = Firebase.analytics
        firestore = Firebase.firestore
        addGoogleAnalyticsTrackEvent()
        renderProduct()
    }

    override fun addGoogleAnalyticsTrackEvent() {
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
            param(FirebaseAnalytics.Param.SCREEN_NAME, product.name!!)
            param(FirebaseAnalytics.Param.SCREEN_CLASS, "ProductActivity")
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
    private  fun renderProduct(){
        binding.tvName.text = product.name
        binding.tvDescription.text = product.description
        Glide.with(this).load(product.img).centerCrop().into(binding.imageView)

    }
}