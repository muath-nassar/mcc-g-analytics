package com.example.googleanalytics

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.googleanalytics.models.TrackedActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase

class ProductActivity : AppCompatActivity(),TrackedActivity {
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    private lateinit var productName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)
        firebaseAnalytics = Firebase.analytics
        addGoogleAnalyticsTrackEvent()
    }

    override fun addGoogleAnalyticsTrackEvent() {
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
            param(FirebaseAnalytics.Param.SCREEN_NAME, productName)
            param(FirebaseAnalytics.Param.SCREEN_CLASS, "ProductActivity")
        }
    }
}