package com.expensetracker.spikes.s04

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony

/** New-message receipt only. The app asks for RECEIVE_SMS and never READ_SMS, so there is no inbox import. */
class SmsReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != Telephony.Sms.Intents.SMS_RECEIVED_ACTION) return
        val parts = Telephony.Sms.Intents.getMessagesFromIntent(intent)
        if (parts.isEmpty()) return
        val sender = parts.first().originatingAddress ?: "unknown"
        val body = parts.joinToString("") { it.messageBody.orEmpty() }
        val sentAt = parts.first().timestampMillis
        val delayMs = System.currentTimeMillis() - sentAt
        SpikeLog.add(context, "sms", sender, "delay=${delayMs}ms ${AlertRedactor.redact(body)}")
    }
}