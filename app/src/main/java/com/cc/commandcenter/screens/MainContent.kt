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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cc.commandcenter.components.CardPlaceholder
import com.cc.commandcenter.model.Card
import com.cc.commandcenter.model.Screen
import com.cc.commandcenter.subtitleFor
import com.cc.commandcenter.ui.theme.CcMuted
import com.cc.commandcenter.ui.theme.CcText

@Composable
fun MainContent(
    screen: Screen,
    cards: List<Card>,
    onSaveCard: (Card) -> Unit
) {
    var selectedCard by remember { mutableStateOf<Card?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(36.dp))
            .background(Color(0xFF15130F))
            .padding(32.dp)
    ) {
        Text(
            text = if (selectedCard == null) screen.title else "Card bewerken",
            color = CcText,
            fontSize = 38.sp,
            fontWeight = FontWeight.Light
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = if (selectedCard == null) {
                subtitleFor(screen)
            } else {
                "Pas de Card aan en sla je wijziging op."
            },
            color = CcMuted,
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        val activeCard = selectedCard

        if (activeCard != null) {
            CardDetailScreen(
                card = activeCard,
                onSave = { updatedCard ->
                    onSaveCard(updatedCard)
                    selectedCard = null
                }
            )
        } else {
            when (screen) {
                Screen.TODAY -> TodayScreen(
                    cards = cards,
                    onCardClick = { selectedCard = it }
                )

                Screen.FOCUS -> FocusScreen(
                    cards = cards,
                    onCardClick = { selectedCard = it }
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
}