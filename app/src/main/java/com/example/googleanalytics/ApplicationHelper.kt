package com.example.googleanalytics

import android.app.Application

class ApplicationHelper: Application() {
    var userId = "12356" //This should be modified to get the actual Id, but this is a demo
    override fun onCreate() {
        super.onCreate()

    }
}