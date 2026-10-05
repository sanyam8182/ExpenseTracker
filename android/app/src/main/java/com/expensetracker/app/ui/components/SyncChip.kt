package com.expensetracker.app.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.expensetracker.app.R
import com.expensetracker.app.ui.theme.ExpenseTrackerTheme
import com.expensetracker.app.ui.theme.PillShape
import com.expensetracker.app.ui.theme.Spacing

/** The seven freshness states of FBK 03. Fifteen minutes is a display threshold, not a sync promise. */
sealed interface SyncStatus {
    data class Synced(val minutesAgo: Int) : SyncStatus
    data object Syncing : SyncStatus
    data object Pending : SyncStatus
    data class Stale(val minutesAgo: Int) : SyncStatus
    data object NeverSynced : SyncStatus
    data object Failed : SyncStatus
    data object AccessRevoked : SyncStatus
}

private data class ChipLook(val container: Color, val content: Color, val border: Color?, val icon: ImageVector)

@Composable
private fun SyncStatus.look(): ChipLook {
    val colors = MaterialTheme.colorScheme
    val extra = ExpenseTrackerTheme.extraColors
    // Placeholder icons from the core set; the real build uses Material Symbols.
    return when (this) {
        is SyncStatus.Synced -> ChipLook(colors.surface, colors.onSurfaceVariant, colors.outlineVariant, Icons.Filled.CheckCircle)
        SyncStatus.Syncing -> ChipLook(colors.surface, colors.onSurfaceVariant, colors.outlineVariant, Icons.Filled.Refresh)
        SyncStatus.Pending -> ChipLook(colors.surface, colors.onSurfaceVariant, colors.outlineVariant, Icons.Filled.Send)
        is SyncStatus.Stale, SyncStatus.NeverSynced -> ChipLook(extra.warningContainer, extra.warning, null, Icons.Filled.Warning)
        SyncStatus.Failed -> ChipLook(colors.errorContainer, colors.error, null, Icons.Filled.Warning)
        SyncStatus.AccessRevoked -> ChipLook(colors.errorContainer, colors.error, null, Icons.Filled.Lock)
    }
}

@Composable
private fun SyncStatus.text(): String = when (this) {
    is SyncStatus.Synced -> if (minutesAgo <= 0) stringResource(R.string.sync_synced_just_now) else stringResource(R.string.sync_synced_minutes, minutesAgo)
    SyncStatus.Syncing -> stringResource(R.string.sync_syncing)
    SyncStatus.Pending -> stringResource(R.string.sync_pending)
    is SyncStatus.Stale -> stringResource(R.string.sync_stale_minutes, minutesAgo)
    SyncStatus.NeverSynced -> stringResource(R.string.sync_never)
    SyncStatus.Failed -> stringResource(R.string.sync_failed)
    SyncStatus.AccessRevoked -> stringResource(R.string.sync_revoked)
}

/** Top-bar freshness chip. The touch area is 48dp even though the chip looks smaller; a tap refreshes. */
@Composable
fun SyncChip(status: SyncStatus, onRefresh: () -> Unit, modifier: Modifier = Modifier) {
    val look = status.look()
    val shape = PillShape
    Box(
        modifier = modifier
            .minimumInteractiveComponentSize()
            .clickable(onClickLabel = stringResource(R.string.sync_refresh_action), role = Role.Button, onClick = onRefresh),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            modifier = Modifier
                .background(look.container, shape)
                .then(if (look.border != null) Modifier.border(BorderStroke(1.dp, look.border), shape) else Modifier)
                .padding(horizontal = Spacing.s3, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Spacing.s1),
        ) {
            Icon(imageVector = look.icon, contentDescription = null, tint = look.content, modifier = Modifier.size(16.dp))
            Text(text = status.text(), style = MaterialTheme.typography.labelSmall, color = look.content)
        }
    }
}
