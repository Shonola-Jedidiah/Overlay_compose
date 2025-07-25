package com.luhyah.overlay

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build

class OverlayApp: Application() {

    override fun onCreate() {
        super.onCreate()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel("Overlay_Channel", "Overlay App", NotificationManager.IMPORTANCE_HIGH)
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

    }
}