package com.example.googleanalytics

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.googleanalytics.databinding.ActivityFoodBinding
import com.example.googleanalytics.databinding.ActivityMainBinding
import com.example.googleanalytics.models.TrackedActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase

class FoodCategoryActivity : AppCompatActivity(),TrackedActivity {
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    private lateinit var binder : ActivityFoodBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = ActivityFoodBinding.inflate(layoutInflater)
        setContentView(binder.root)
        firebaseAnalytics = Firebase.analytics
        addGoogleAnalyticsTrackEvent()

    }

    override fun addGoogleAnalyticsTrackEvent() {
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
            param(FirebaseAnalytics.Param.SCREEN_NAME, "Food")
            param(FirebaseAnalytics.Param.SCREEN_CLASS, localClassName)
        }
    }
}