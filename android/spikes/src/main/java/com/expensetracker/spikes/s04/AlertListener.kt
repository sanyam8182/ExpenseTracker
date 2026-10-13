package com.expensetracker.spikes.s04

import android.app.Notification
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification

class AlertListener : NotificationListenerService() {
    override fun onListenerConnected() {
        SpikeLog.add(this, "listener-connected", packageName)
    }

    override fun onListenerDisconnected() {
        SpikeLog.add(this, "listener-disconnected", packageName)
    }

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        if (CapturePolicy.decide(sbn.packageName, CapturePolicy.load(this)) == Capture.PackageOnly) {
            SpikeLog.add(this, "notification-ignored", sbn.packageName)
            return
        }
        val extras = sbn.notification.extras
        val title = extras.getCharSequence(Notification.EXTRA_TITLE)?.toString().orEmpty()
        val text = extras.getCharSequence(Notification.EXTRA_BIG_TEXT)?.toString()
            ?: extras.getCharSequence(Notification.EXTRA_TEXT)?.toString().orEmpty()
        val shown = AlertRedactor.redact("$title | $text")
        SpikeLog.add(this, "notification", sbn.packageName, "key=${sbn.key.hashCode()} ongoing=${sbn.isOngoing} $shown")
    }
}