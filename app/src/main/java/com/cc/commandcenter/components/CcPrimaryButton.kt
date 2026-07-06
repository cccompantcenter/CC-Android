package com.cc.commandcenter.components

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun CcPrimaryButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick
    ) {
        Text(text)
    }
}