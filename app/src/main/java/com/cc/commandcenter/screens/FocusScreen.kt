package com.cc.commandcenter.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.cc.commandcenter.components.CcCard
import com.cc.commandcenter.model.Card
import com.cc.commandcenter.model.CardCategory
import com.cc.commandcenter.model.CardPriority
import com.cc.commandcenter.model.CardStatus

@Composable
fun FocusScreen() {
    var status by remember { mutableStateOf(CardStatus.OPEN) }

    val card = Card(
        id = 1L,
        title = "Eerste echte CC Card",
        description = "Deze Card gebruikt nu het centrale Card-model én het herbruikbare CcCard-component.",
        category = CardCategory.FOCUS,
        priority = CardPriority.HIGH,
        status = status,
        createdLabel = "Vandaag"
    )

    CcCard(
        card = card,
        onToggleStatus = {
            status = if (status == CardStatus.OPEN) {
                CardStatus.COMPLETED
            } else {
                CardStatus.OPEN
            }
        }
    )
}