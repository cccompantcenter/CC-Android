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
fun TodayScreen(
    cards: List<Card>
) {
    val todayCards = cards.filter {
        it.category == CardCategory.TODAY &&
        it.status == CardStatus.OPEN
    }

    val cardStatuses = remember {
        mutableStateMapOf<Long, CardStatus>()
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        todayCards.forEach { card ->

            val currentStatus = cardStatuses[card.id] ?: card.status
            val visibleCard = card.copy(status = currentStatus)

            if (visibleCard.status == CardStatus.OPEN) {
                CcCard(
                    card = visibleCard,
                    onToggleStatus = {
                        val newStatus =
                            if (visibleCard.status == CardStatus.OPEN) {
                                CardStatus.COMPLETED
                            } else {
                                CardStatus.OPEN
                            }

                        cardStatuses[card.id] = newStatus
                    },
                    onClick = {
                        // Navigatie volgt in een volgende sprint.
                    }
                )
            }
        }
    }
}