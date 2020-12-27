package com.cardsplusplus.cards

import android.app.Application
import android.content.Context

class App : Application() {
    companion object {
        lateinit var context: Context private set

        val deviceWidth: Int
            get() = context.resources.displayMetrics.widthPixels

        val deviceHeight: Int
            get() = context.resources.displayMetrics.heightPixels
    }

    override fun onCreate() {
        super.onCreate()
        context = this.applicationContext


    }




}