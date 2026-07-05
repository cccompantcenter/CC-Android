package com.cc.commandcenter.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cc.commandcenter.components.CardPlaceholder
import com.cc.commandcenter.model.Screen
import com.cc.commandcenter.subtitleFor
import com.cc.commandcenter.ui.theme.CcMuted
import com.cc.commandcenter.ui.theme.CcText

@Composable
fun MainContent(screen: Screen) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(36.dp))
            .background(Color(0xFF15130F))
            .padding(32.dp)
    ) {
        Text(
            text = screen.title,
            color = CcText,
            fontSize = 38.sp,
            fontWeight = FontWeight.Light
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = subtitleFor(screen),
            color = CcMuted,
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        when (screen) {
            Screen.TODAY -> TodayScreen()
            Screen.FOCUS -> CardPlaceholder(
                title = "Focus",
                body = "Dit wordt de eerste actieve Card achter Vandaag."
            )
            Screen.MY_TASKS -> CardPlaceholder(
                title = "Mijn taken",
                body = "Hier komen straks jouw eigen Cards."
            )
            Screen.WAITING -> CardPlaceholder(
                title = "Reactie afwachten",
                body = "Hier komen Cards waarbij je wacht op iemand anders."
            )
            Screen.OTHERS -> CardPlaceholder(
                title = "Taken van anderen",
                body = "Hier komen Cards die bij een ander liggen, maar wel jouw aandacht vragen."
            )
            Screen.IDEAS -> CardPlaceholder(
                title = "Ideeën",
                body = "Hier bewaar je losse gedachten voordat ze actie worden."
            )
            Screen.ARCHIVE -> CardPlaceholder(
                title = "Archief",
                body = "Hier komen afgeronde of bewaarde Cards."
            )
        }
    }
}