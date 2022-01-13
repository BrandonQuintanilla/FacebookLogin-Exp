package com.example.facebooklogin

import android.app.Application
import com.facebook.appevents.AppEventsLogger

import com.facebook.FacebookSdk

class FbApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        FacebookSdk.sdkInitialize(applicationContext)
        AppEventsLogger.activateApp(this)
    }

}