package com.cc.commandcenter.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cc.commandcenter.ui.theme.CcGold
import com.cc.commandcenter.ui.theme.CcMidnight

@Composable
fun CcRememberButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    badgeCount: Int = 0,
    onBadgeClick: (() -> Unit)? = null
) {
    Box(modifier = modifier.size(72.dp)) {
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
            CcLogo(
                modifier = Modifier.size(42.dp)
            )
        }

        if (badgeCount > 0) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF1F1A12))
                    .clickable { onBadgeClick?.invoke() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "+$badgeCount",
                    color = CcGold,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}