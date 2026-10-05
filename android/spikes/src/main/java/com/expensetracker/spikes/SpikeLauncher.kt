package com.expensetracker.spikes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class Spike(val id: String, val jira: String, val question: String)

val Spikes = listOf(
    Spike("SPIKE-01", "SCRUM-39", "Which Google Drive and Sheets permissions are enough, and does consent work?"),
    Spike("SPIKE-02", "SCRUM-40", "Does sharing a sheet join a second person automatically, and can they be removed?"),
    Spike("SPIKE-03", "SCRUM-20", "Can the parser read alerts from five real banks?"),
    Spike("SPIKE-04", "SCRUM-12", "Can the app see bank SMS and notifications on Samsung with minimal permissions?"),
    Spike("SPIKE-05", "SCRUM-13", "Do the encrypted database, Keystore key and export work on the target phones?"),
)

class SpikeLauncher : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Column(Modifier.fillMaxSize().statusBarsPadding().padding(16.dp)) {
                    Text("Gate 0 spikes", style = MaterialTheme.typography.headlineMedium)
                    Spikes.forEach { spike ->
                        Text(
                            "${spike.id} (${spike.jira})",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(top = 16.dp),
                        )
                        Text(spike.question, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}