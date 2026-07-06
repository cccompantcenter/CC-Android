package com.cc.commandcenter.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cc.commandcenter.ui.theme.CcText

@Composable
fun CcHeader(
    title: String,
    subtitle: String? = null
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = title,
            color = CcText,
            fontSize = 28.sp
        )

        if (subtitle != null) {
            Text(
                text = subtitle,
                color = CcText,
                fontSize = 16.sp
            )
        }
    }
}