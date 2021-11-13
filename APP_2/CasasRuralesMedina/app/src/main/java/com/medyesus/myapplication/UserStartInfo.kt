package com.medyesus.myapplication

import android.app.Application

// Esta clase se ejecutará siempre que se abra la aplicación antes de pasar al main activity
class UserStartInfo:Application(){
    companion object{
        lateinit var prefs:Prefs
    }
    override fun onCreate() {
        super.onCreate()

        prefs = Prefs(applicationContext)
    }
}