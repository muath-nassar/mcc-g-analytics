package com.example.googleanalytics.models

interface TrackedActivity {
    fun addGoogleAnalyticsTrackEvent()
    fun onStartTimer()
    fun onStopTimer()
}