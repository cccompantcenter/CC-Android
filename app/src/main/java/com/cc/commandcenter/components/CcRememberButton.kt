package com.cc.commandcenter.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cc.commandcenter.ui.theme.CcGold
import com.cc.commandcenter.ui.theme.CcMidnight

@Composable
fun CcRememberButton(
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        OutlinedButton(
            onClick = onClick,
            modifier = Modifier.size(72.dp),
            shape = CircleShape,
            border = BorderStroke(2.dp, CcGold),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = CcMidnight,
                contentColor = CcGold
            ),
            contentPadding = PaddingValues(0.dp)
        ) {
            Text(
                text = "CC",
                fontSize = 20.sp
            )
        }

        Text(
            text = "Onthoud",
            color = CcGold,
            fontSize = 12.sp
        )
    }
}