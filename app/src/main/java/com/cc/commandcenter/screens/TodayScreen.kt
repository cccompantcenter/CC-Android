package com.cc.commandcenter.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import com.cc.commandcenter.components.CcCard
import com.cc.commandcenter.data.CardRepository
import com.cc.commandcenter.model.CardStatus

@Composable
fun TodayScreen() {
    val cards = CardRepository.todayCards()
        .filter { it.status == CardStatus.OPEN }

    val cardStatuses = remember {
        mutableStateMapOf<Long, CardStatus>()
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        cards.forEach { card ->
            val currentStatus = cardStatuses[card.id] ?: card.status
            val visibleCard = card.copy(status = currentStatus)

            if (visibleCard.status == CardStatus.OPEN) {
                CcCard(
                    card = visibleCard,
                    onToggleStatus = {
                        cardStatuses[card.id] = CardStatus.COMPLETED
                    }
                )
            }
        }
    }
}