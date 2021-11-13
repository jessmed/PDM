package com.medyesus.myapplication

import android.content.Context

class Prefs(val context: Context) {
    val SHARED_NAME = "Mydatabase"
    val SHARED_SESSION = "sesion"
    val SHARED_LANGUAGE = "language"
    val SHARED_EMAIL = "email"
    val storage = context.getSharedPreferences(SHARED_NAME,0)

    fun saveSession(b:Boolean){
        storage.edit().putBoolean(SHARED_SESSION,b).apply()
    }
    fun saveLanguage(language:String){
        storage.edit().putString(SHARED_LANGUAGE,language).apply()
    }
    fun saveEmail(email:String){
        storage.edit().putString(SHARED_EMAIL,email).apply()
    }
    fun getLanguage():String{
        return storage.getString(SHARED_LANGUAGE, "en")!!
    }
    fun getSession():Boolean{
        return storage.getBoolean(SHARED_SESSION, false)
    }
    fun getEmail():String{
        return storage.getString(SHARED_EMAIL, "")!!
    }
}