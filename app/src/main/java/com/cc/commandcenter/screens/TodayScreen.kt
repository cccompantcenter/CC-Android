package com.cc.commandcenter.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cc.commandcenter.components.CcCard
import com.cc.commandcenter.components.CcHeader
import com.cc.commandcenter.components.CcSectionHeader
import com.cc.commandcenter.model.Card
import com.cc.commandcenter.model.CardCategory
import com.cc.commandcenter.model.CardStatus
import com.cc.commandcenter.ui.theme.CcText
import java.time.LocalDate

@Composable
fun TodayScreen(
    cards: List<Card>,
    onCardClick: (Card) -> Unit
) {
    val today = LocalDate.now()

    val todayCards = cards.filter { card ->
        card.status == CardStatus.OPEN &&
            card.dueDate != null &&
            LocalDate.parse(card.dueDate) <= today
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        CcHeader(
            title = "Goedemorgen Chantal",
            subtitle = "Vandaag vraagt aandacht"
        )

        if (todayCards.isEmpty()) {
            EmptyTodayState()
            return@Column
        }

        TodaySection(
            title = "Focus",
            cards = todayCards
                .filter { it.category == CardCategory.FOCUS }
                .take(3),
            onCardClick = onCardClick
        )

        TodaySection(
            title = "Vandaag op te pakken",
            cards = todayCards.filter { it.category == CardCategory.MY_TASKS },
            onCardClick = onCardClick
        )

        TodaySection(
            title = "Reactie afwachten",
            cards = todayCards.filter { it.category == CardCategory.WAITING },
            onCardClick = onCardClick
        )

        TodaySection(
            title = "Taken van anderen",
            cards = todayCards.filter { it.category == CardCategory.OTHERS },
            onCardClick = onCardClick
        )
    }
}

@Composable
private fun EmptyTodayState() {
    Text(
        text = "Geen open Cards die vandaag aandacht vragen.",
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