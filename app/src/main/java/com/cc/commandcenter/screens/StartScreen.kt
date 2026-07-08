package com.cc.commandcenter.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.cc.commandcenter.components.CcLogo
import com.cc.commandcenter.components.CcPrimaryButton
import com.cc.commandcenter.components.CcRememberButton
import kotlinx.coroutines.delay

@Composable
private fun FadeInCcLogo(
    modifier: Modifier = Modifier,
    durationMillis: Int = 1000
) {
    val alpha by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(durationMillis = durationMillis),
        label = "ccLogoFadeIn"
    )

    CcLogo(
        modifier = modifier.alpha(alpha)
    )
}

@Composable
fun StartScreen(
    onOpenDashboard: () -> Unit,
    onOpenQuickNote: () -> Unit,
    onOpenGedachten: () -> Unit
) {
    LaunchedEffect(Unit) {
        delay(2500)
        onOpenDashboard()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        FadeInCcLogo(
            modifier = Modifier.size(420.dp)
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CcPrimaryButton(
                text = "Gedachten",
                onClick = onOpenGedachten
            )

            CcRememberButton(
                onClick = onOpenQuickNote,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}