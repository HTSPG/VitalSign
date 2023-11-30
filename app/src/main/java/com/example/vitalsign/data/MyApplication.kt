package com.example.vitalsign.data

import android.app.Application

class MyApplication : Application() {
    var globalData: String? = null

    override fun onCreate() {
        super.onCreate()
        // 초기화 코드
    }
}