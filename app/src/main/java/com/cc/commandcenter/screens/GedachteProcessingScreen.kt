package com.cc.commandcenter.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cc.commandcenter.components.CcPrimaryButton
import com.cc.commandcenter.model.QuickNote
import com.cc.commandcenter.ui.theme.CcMidnight
import com.cc.commandcenter.ui.theme.CcMuted
import com.cc.commandcenter.ui.theme.CcText

@Composable
fun GedachteProcessingScreen(
    gedachte: QuickNote,
    onBack: () -> Unit,
    onMaakCard: () -> Unit,
    onBewaarAlsGedachte: () -> Unit,
    onArchiveer: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CcMidnight)
            .padding(horizontal = 24.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = 760.dp)
                .clip(RoundedCornerShape(32.dp))
                .background(Color(0xFF1C1A14))
                .border(
                    width = 1.dp,
                    color = Color(0xFFC8A75D).copy(alpha = 0.22f),
                    shape = RoundedCornerShape(32.dp)
                )
                .padding(28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text(
                text = "Verwerk Gedachte",
                color = CcText,
                fontSize = 34.sp,
                fontWeight = FontWeight.Light
            )

            Text(
                text = "Kies rustig wat je met deze gedachte wilt doen.",
                color = CcMuted,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = gedachteTitle(gedachte),
                color = CcText,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(6.dp))

            CcPrimaryButton(
                text = "Maak Card",
                onClick = onMaakCard,
                modifier = Modifier.fillMaxWidth()
            )

            CcPrimaryButton(
                text = "Bewaar als Gedachte",
                onClick = onBewaarAlsGedachte,
                modifier = Modifier.fillMaxWidth()
            )

            CcPrimaryButton(
                text = "Archiveer",
                onClick = onArchiveer,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(4.dp))

            CcPrimaryButton(
                text = "← Terug",
                onClick = onBack,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

private fun gedachteTitle(gedachte: QuickNote): String {
    return gedachte.text.lines()
        .map { it.trim() }
        .firstOrNull { it.isNotEmpty() }
        ?: "Nieuwe gedachte"
}
