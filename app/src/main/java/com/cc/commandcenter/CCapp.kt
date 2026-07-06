package com.cc.commandcenter
import com.cc.commandcenter.screens.StartScreen
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

@Composable
fun CCApp() {
    var showStartScreen by remember { mutableStateOf(true) }
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

    fun createCard(
        title: String,
        category: CardCategory,
        priority: CardPriority,
        dueDate: String
    ): Card {
        val newCard = Card(
            id = (cards.maxOfOrNull { it.id } ?: 0L) + 1L,
            title = title,
            description = "",
            category = category,
            priority = priority,
            status = CardStatus.OPEN,
            createdLabel = "Vandaag",
            dueDate = dueDate
        )

        cards.add(0, newCard)
        return newCard
    }

    fun deleteCard(card: Card) {
        cards.removeAll { it.id == card.id }
    }
    if (showStartScreen) {
    StartScreen(
        onOpenDashboard = {
            showStartScreen = false
        }
    )
    return
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
            onDeleteCard = { card ->
                deleteCard(card)
            },
            onCreateCard = { title, category, priority, dueDate ->
                createCard(
                    title = title,
                    category = category,
                    priority = priority,
                    dueDate = dueDate
                )
            }
        )
    }
}