package com.cc.commandcenter.components

import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cc.commandcenter.ui.theme.CcGold
import com.cc.commandcenter.ui.theme.CcMidnight

@Composable
fun CcPrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = CcGold,
            contentColor = CcMidnight
        )
    ) {
        Text(text)
    }
}