package com.cc.commandcenter.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cc.commandcenter.components.CcLogo
import com.cc.commandcenter.components.CcPrimaryButton
import com.cc.commandcenter.ui.theme.CcMidnight

@Composable
fun StartScreen(
    onOpenDashboard: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CcMidnight),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        CcLogo(
            modifier = Modifier.size(140.dp)
        )

        Spacer(modifier = Modifier.height(48.dp))

        CcPrimaryButton(
            text = "Directe notitie",
            onClick = {
                // volgt in de volgende stap
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        CcPrimaryButton(
            text = "Dashboard openen",
            onClick = onOpenDashboard
        )
    }
}