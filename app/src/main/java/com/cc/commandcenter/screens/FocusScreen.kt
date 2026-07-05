package com.cc.commandcenter.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import com.cc.commandcenter.components.CcCard
import com.cc.commandcenter.model.Card
import com.cc.commandcenter.model.CardCategory
import com.cc.commandcenter.model.CardStatus

@Composable
fun FocusScreen(
    cards: List<Card>,
    onCardClick: (Card) -> Unit
) {
    val focusCards = cards.filter {
        it.category == CardCategory.FOCUS
    }

    val cardStatuses = remember {
        mutableStateMapOf<Long, CardStatus>()
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        focusCards.forEach { card ->

            val currentStatus = cardStatuses[card.id] ?: card.status
            val visibleCard = card.copy(status = currentStatus)

            CcCard(
                card = visibleCard,
                onToggleStatus = {
                    cardStatuses[card.id] =
                        if (currentStatus == CardStatus.OPEN) {
                            CardStatus.COMPLETED
                        } else {
                            CardStatus.OPEN
                        }
                },
                onClick = {
                    onCardClick(visibleCard)
                }
            )
        }
    }
}