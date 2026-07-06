package com.cc.commandcenter.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Text
import com.cc.commandcenter.components.CcCard
import com.cc.commandcenter.components.CcHeader
import com.cc.commandcenter.components.CcSectionHeader
import com.cc.commandcenter.model.Card
import com.cc.commandcenter.model.CardCategory
import com.cc.commandcenter.model.CardStatus
import com.cc.commandcenter.ui.theme.CcText

@Composable
fun TodayScreen(
    cards: List<Card>,
    onCardClick: (Card) -> Unit
) {
    val openCards = cards.filter { it.status == CardStatus.OPEN }

    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        CcHeader(
            title = "Goedemorgen Chantal",
            subtitle = "Vandaag"
        )

        if (openCards.isEmpty()) {
            EmptyTodayState()
            return@Column
        }

        TodaySection(
            title = "Focus",
            cards = openCards
                .filter { it.category == CardCategory.FOCUS }
                .take(3),
            onCardClick = onCardClick
        )

        TodaySection(
            title = "Vandaag op te pakken",
            cards = openCards.filter { it.category == CardCategory.MY_TASKS },
            onCardClick = onCardClick
        )

        TodaySection(
            title = "Reactie afwachten",
            cards = openCards.filter { it.category == CardCategory.WAITING },
            onCardClick = onCardClick
        )

        TodaySection(
            title = "Ideeën",
            cards = openCards.filter { it.category == CardCategory.IDEAS },
            onCardClick = onCardClick
        )
    }
}

@Composable
private fun EmptyTodayState() {
    Text(
        text = "Geen open kaarten voor vandaag.",
        color = CcText,
        fontSize = 16.sp
    )
}

@Composable
private fun TodaySection(
    title: String,
    cards: List<Card>,
    onCardClick: (Card) -> Unit
) {
    if (cards.isEmpty()) return

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        CcSectionHeader(
            title = title
        )

        cards.forEach { card ->
            CcCard(
                card = card,
                onToggleStatus = {
                    // Status wijzigen volgt later centraal.
                },
                onClick = {
                    onCardClick(card)
                }
            )
        }
    }
}