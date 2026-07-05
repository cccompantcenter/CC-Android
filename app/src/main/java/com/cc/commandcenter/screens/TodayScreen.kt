package com.cc.commandcenter.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import com.cc.commandcenter.components.CcCard
import com.cc.commandcenter.data.CardRepository
import com.cc.commandcenter.model.Card
import com.cc.commandcenter.model.CardStatus

@Composable
fun TodayScreen() {
    val cards = CardRepository.todayCards()
    val selectedCard = remember { mutableStateOf<Card?>(null) }

    val cardStatuses = remember {
        mutableStateMapOf<Long, CardStatus>()
    }

    if (selectedCard.value != null) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = {
                    selectedCard.value = null
                }
            ) {
                Text("Terug")
            }

            CardDetailScreen(
                card = selectedCard.value!!
            )
        }
    } else {
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
                        },
                        onClick = {
                            selectedCard.value = visibleCard
                        }
                    )
                }
            }
        }
    }
}