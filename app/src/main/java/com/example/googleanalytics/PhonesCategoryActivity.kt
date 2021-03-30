package com.example.googleanalytics

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.googleanalytics.models.TrackedActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase

class PhonesCategoryActivity : AppCompatActivity(),TrackedActivity {
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phones_category)
        firebaseAnalytics = Firebase.analytics
        addGoogleAnalyticsTrackEvent()
    }

    override fun addGoogleAnalyticsTrackEvent() {
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
            param(FirebaseAnalytics.Param.SCREEN_NAME, "Phones")
            param(FirebaseAnalytics.Param.SCREEN_CLASS, localClassName)
        }
    }
}