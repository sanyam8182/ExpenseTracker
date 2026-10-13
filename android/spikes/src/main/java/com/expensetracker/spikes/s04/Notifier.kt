package com.expensetracker.spikes.s04

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat

/** Posts the app's own notification, the third capability: it never touches SMS or other apps' notifications. */
object Notifier {
    private const val CHANNEL_ID = "spike04"
    private const val NOTIFICATION_ID = 4

    fun canPost(context: Context): Boolean =
        ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED &&
            context.getSystemService(NotificationManager::class.java).areNotificationsEnabled()

    /** Returns false when posting is blocked, so the caller can show in-app feedback instead (CAP 01). */
    fun post(context: Context, text: String): Boolean {
        val manager = context.getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(NotificationChannel(CHANNEL_ID, "Spike test", NotificationManager.IMPORTANCE_DEFAULT))
        if (!canPost(context)) return false
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Spike 04 test")
            .setContentText(text)
            .build()
        manager.notify(NOTIFICATION_ID, notification)
        return true
    }
}