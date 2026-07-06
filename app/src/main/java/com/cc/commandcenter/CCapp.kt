package com.cc.commandcenter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cc.commandcenter.components.Sidebar
import com.cc.commandcenter.data.CardRepository
import com.cc.commandcenter.model.Card
import com.cc.commandcenter.model.CardCategory
import com.cc.commandcenter.model.CardPriority
import com.cc.commandcenter.model.CardStatus
import com.cc.commandcenter.model.Screen
import com.cc.commandcenter.screens.MainContent
import com.cc.commandcenter.ui.theme.CcMidnight
import java.time.LocalDate

@Composable
fun CCApp() {
    var currentScreen by remember { mutableStateOf(Screen.TODAY) }

    val cards = remember {
        mutableStateListOf(
            *CardRepository.allCards().toTypedArray()
        )
    }

    fun saveCard(updatedCard: Card) {
        val index = cards.indexOfFirst { it.id == updatedCard.id }

        if (index != -1) {
            cards[index] = updatedCard
        }
    }

    fun addCard(): Card {
        val newCard = Card(
            id = (cards.maxOfOrNull { it.id } ?: 0L) + 1L,
            title = "Nieuwe Card",
            description = "",
            category = CardCategory.MY_TASKS,
            priority = CardPriority.NORMAL,
            status = CardStatus.OPEN,
            createdLabel = "Vandaag",
            dueDate = LocalDate.now().toString()
        )

        cards.add(0, newCard)
        return newCard
    }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(CcMidnight)
            .padding(24.dp)
    ) {
        Sidebar(
            currentScreen = currentScreen,
            onScreenSelected = { currentScreen = it }
        )

        Spacer(modifier = Modifier.width(24.dp))

        MainContent(
            screen = currentScreen,
            cards = cards,
            onSaveCard = { updatedCard ->
                saveCard(updatedCard)
            },
            onAddCard = {
                addCard()
            }
        )
    }
}