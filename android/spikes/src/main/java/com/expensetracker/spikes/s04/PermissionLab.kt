package com.expensetracker.spikes.s04

import android.Manifest
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.PowerManager
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LifecycleResumeEffect

/** Three capabilities, each with its own status and recovery, plus the battery state that decides background receipt. */
class PermissionLab : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MaterialTheme { Lab() } }
    }
}

private fun smsGranted(c: Context) =
    ContextCompat.checkSelfPermission(c, Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED

private fun listenerEnabled(c: Context) = NotificationManagerCompat.getEnabledListenerPackages(c).contains(c.packageName)

private fun batteryUnrestricted(c: Context) =
    c.getSystemService(PowerManager::class.java).isIgnoringBatteryOptimizations(c.packageName)

private fun standbyBucket(c: Context): String =
    when (val bucket = c.getSystemService(UsageStatsManager::class.java).appStandbyBucket) {
        UsageStatsManager.STANDBY_BUCKET_ACTIVE -> "active"
        UsageStatsManager.STANDBY_BUCKET_WORKING_SET -> "working set"
        UsageStatsManager.STANDBY_BUCKET_FREQUENT -> "frequent"
        UsageStatsManager.STANDBY_BUCKET_RARE -> "rare"
        UsageStatsManager.STANDBY_BUCKET_RESTRICTED -> "restricted"
        else -> "other ($bucket)"
    }

@Composable
private fun Lab() {
    val context = LocalContext.current
    var refresh by remember { mutableIntStateOf(0) }
    var message by remember { mutableStateOf("") }
    var postNote by remember { mutableStateOf("") }
    LifecycleResumeEffect(Unit) {
        refresh++
        onPauseOrDispose { }
    }
    val askSms = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { refresh++ }
    val askPost = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { refresh++ }
    val state = refresh // read so the rows recompute after each return

    Column(
        Modifier.fillMaxSize().statusBarsPadding().verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text("SPIKE-04 lab", style = MaterialTheme.typography.headlineSmall)
        Text("Build ${android.os.Build.DISPLAY}, Android ${android.os.Build.VERSION.RELEASE}", fontSize = 12.sp)

        Capability("1. SMS receipt (RECEIVE_SMS)", if (state >= 0 && smsGranted(context)) "granted" else "not granted") {
            Button(onClick = { askSms.launch(Manifest.permission.RECEIVE_SMS) }) { Text("Ask") }
            OutlinedButton(onClick = { openAppInfo(context) }) { Text("App info") }
        }
        Capability("2. Notification access (listener)", if (state >= 0 && listenerEnabled(context)) "enabled" else "not enabled") {
            Button(onClick = { context.startActivity(Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS)) }) { Text("Open access settings") }
            OutlinedButton(onClick = { openAppInfo(context) }) { Text("App info") }
        }
        Capability("3. Posting own notifications", if (state >= 0 && Notifier.canPost(context)) "allowed" else "blocked", note = postNote) {
            Button(onClick = { askPost.launch(Manifest.permission.POST_NOTIFICATIONS) }) { Text("Ask") }
            OutlinedButton(onClick = {
                postNote = if (Notifier.post(context, "Your Spike 04 test alert for Rs.640 at ZXCV SHOP")) {
                    "Test notification posted."
                } else {
                    "Posting is blocked, so this message is shown in the app instead (manual fallback works)."
                }
            }) { Text("Post test") }
        }
        Capability(
            "4. Battery",
            "${if (state >= 0 && batteryUnrestricted(context)) "unrestricted" else "optimised"}; standby bucket: ${standbyBucket(context)}",
        ) {
            OutlinedButton(onClick = { context.startActivity(Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS)) }) {
                Text("Battery optimisation list")
            }
        }
        var packageInput by remember { mutableStateOf("") }
        Capability("Apps whose notification text may be read", CapturePolicy.load(context).sorted().joinToString(", ")) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                OutlinedTextField(
                    value = packageInput,
                    onValueChange = { packageInput = it },
                    label = { Text("Exact package name") },
                    singleLine = true,
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = { CapturePolicy.add(context, packageInput); packageInput = ""; refresh++ }) { Text("Add") }
                    OutlinedButton(onClick = { CapturePolicy.reset(context); refresh++ }) { Text("Reset") }
                }
            }
        }
        Capability("Manual entry (must always work)", "") {
            Button(onClick = {
                SpikeLog.add(context, "manual-entry", context.packageName, "saved Rs.250 cash")
                message = "Saved manually. Works whatever the three capabilities above are set to."
                refresh++
            }) { Text("Save manual expense") }
        }
        if (message.isNotEmpty()) Text(message, color = MaterialTheme.colorScheme.primary)

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Log (redacted, newest first)", style = MaterialTheme.typography.titleMedium)
            OutlinedButton(onClick = { SpikeLog.clear(context); refresh++ }) { Text("Clear") }
        }
        SpikeLog.last(context).also { if (state >= 0 && it.isEmpty()) Text("Nothing yet.") }.forEach {
            Text(it, fontFamily = FontFamily.Monospace, fontSize = 10.sp)
        }
    }
}

@Composable
private fun Capability(title: String, status: String, note: String = "", actions: @Composable () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(title, style = MaterialTheme.typography.titleMedium)
        if (status.isNotEmpty()) Text("Status: $status")
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) { actions() }
        if (note.isNotEmpty()) Text(note, color = MaterialTheme.colorScheme.primary)
    }
}

private fun openAppInfo(context: Context) {
    context.startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", context.packageName, null)))
}