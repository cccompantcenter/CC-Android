package com.cc.commandcenter.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cc.commandcenter.ui.theme.CcGold
import com.cc.commandcenter.ui.theme.CcMidnight

@Composable
fun CcRememberButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.size(72.dp),
        shape = CircleShape,
        border = BorderStroke(2.dp, CcGold),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = CcMidnight,
            contentColor = CcGold
        ),
        contentPadding = PaddingValues(0.dp)
    ) {
        CcLogo(
            modifier = Modifier.size(42.dp)
        )
    }
}