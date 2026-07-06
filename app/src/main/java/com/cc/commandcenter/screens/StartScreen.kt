package com.cc.commandcenter.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.cc.commandcenter.components.CcLogo
import com.cc.commandcenter.components.CcPrimaryButton
import com.cc.commandcenter.ui.theme.CcMidnight

@Composable
fun StartScreen(
    onOpenDashboard: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CcMidnight)
            .padding(horizontal = 72.dp, vertical = 48.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CcLogo(
                modifier = Modifier.size(340.dp)
            )

            Column(
                modifier = Modifier.widthIn(min = 280.dp, max = 360.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CcPrimaryButton(
                    text = "Directe notitie",
                    onClick = {
                        // volgt in latere commit
                    }
                )

                CcPrimaryButton(
                    text = "Dashboard openen",
                    onClick = onOpenDashboard
                )
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "Command Center",
                textAlign = TextAlign.End
            )

            Text(
                text = "Alpha 0.7",
                textAlign = TextAlign.End
            )
        }
    }
}