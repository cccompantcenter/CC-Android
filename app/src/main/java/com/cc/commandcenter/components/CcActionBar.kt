package com.cc.commandcenter.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CcActionBar(
    onBack: () -> Unit,
    onDelete: () -> Unit,
    onSave: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        CcPrimaryButton(
            text = "← Terug",
            onClick = onBack
        )

        CcPrimaryButton(
            text = "Verwijderen",
            onClick = onDelete
        )

        CcPrimaryButton(
            text = "Opslaan",
            onClick = onSave
        )
    }
}