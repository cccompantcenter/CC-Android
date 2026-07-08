package com.cc.commandcenter.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CcActionBar(
    onBack: () -> Unit,
    onDelete: () -> Unit,
    onSave: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        CcPrimaryButton(
            text = "← Terug",
            onClick = onBack,
            modifier = Modifier.weight(1f)
        )

        CcPrimaryButton(
            text = "Verwijderen",
            onClick = onDelete,
            modifier = Modifier.weight(1f)
        )

        CcPrimaryButton(
            text = "Opslaan",
            onClick = onSave,
            modifier = Modifier.weight(1f)
        )
    }
}