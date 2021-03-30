package com.example.googleanalytics

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.googleanalytics.databinding.ActivityClothesCategoryBinding
import com.example.googleanalytics.models.TrackedActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase

class ClothesCategoryActivity : AppCompatActivity(),TrackedActivity {
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    lateinit var binding: ActivityClothesCategoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClothesCategoryBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_clothes_category)
        firebaseAnalytics = Firebase.analytics
    }

    override fun addGoogleAnalyticsTrackEvent() {
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
            param(FirebaseAnalytics.Param.SCREEN_NAME, "Clothes")
            param(FirebaseAnalytics.Param.SCREEN_CLASS, localClassName)
        }
    }
}