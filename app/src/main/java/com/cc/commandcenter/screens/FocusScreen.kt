package com.cc.commandcenter.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.cc.commandcenter.components.CcCard
import com.cc.commandcenter.model.Card
import com.cc.commandcenter.model.CardCategory
import com.cc.commandcenter.model.CardStatus

@Composable
fun FocusScreen(
    cards: List<Card>,
    onCardClick: (Card) -> Unit,
    onToggleStatus: (Card) -> Unit
) {
    val focusCards = cards.filter {
        it.category == CardCategory.FOCUS && it.status == CardStatus.OPEN
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        focusCards.forEach { card ->
            CcCard(
                card = card,
                onToggleStatus = { onToggleStatus(card) },
                onClick = {
                    onCardClick(card)
                }
            )
        }
    }
}