package com.cc.commandcenter.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import com.cc.commandcenter.ui.theme.CcText

@Composable
fun CcSectionHeader(
    title: String
) {
    Text(
        text = title,
        color = CcText,
        fontSize = 22.sp
    )
}