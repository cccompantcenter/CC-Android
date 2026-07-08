package com.cc.commandcenter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cc.commandcenter.components.CcRememberButton
import com.cc.commandcenter.components.Sidebar
import com.cc.commandcenter.data.CardRepository
import com.cc.commandcenter.data.QuickNoteRepository
import com.cc.commandcenter.model.Card
import com.cc.commandcenter.model.CardCategory
import com.cc.commandcenter.model.CardPriority
import com.cc.commandcenter.model.CardStatus
import com.cc.commandcenter.model.Screen
import com.cc.commandcenter.screens.GedachtenScreen
import com.cc.commandcenter.screens.MainContent
import com.cc.commandcenter.screens.QuickNoteScreen
import com.cc.commandcenter.screens.StartScreen
import com.cc.commandcenter.ui.theme.CcMidnight

@Composable
fun CCApp() {
    var showStartScreen by remember { mutableStateOf(true) }
    var showQuickNoteScreen by remember { mutableStateOf(false) }
    var showGedachtenScreen by remember { mutableStateOf(false) }
    var currentScreen by remember { mutableStateOf(Screen.TODAY) }
    var quickNoteReturnsToDashboard by remember { mutableStateOf(false) }
    var selectedGedachte by remember { mutableStateOf<com.cc.commandcenter.model.QuickNote?>(null) }

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
            },
            onOpenQuickNote = {
                quickNoteReturnsToDashboard = true
                showQuickNoteScreen = true
                showStartScreen = false
            },
            onOpenGedachten = {
                showGedachtenScreen = true
                showStartScreen = false
            }
        )
        return
    }

    if (showGedachtenScreen) {
        GedachtenScreen(
            onOpenGedachte = { gedachte ->
                selectedGedachte = gedachte
                showQuickNoteScreen = true
                showGedachtenScreen = false
            }
        )
        return
    }

    if (showQuickNoteScreen) {
        QuickNoteScreen(
            initialNote = selectedGedachte?.text ?: "",
            initialInkStrokes = selectedGedachte?.inkStrokes ?: emptyList(),
            onBack = {
                showQuickNoteScreen = false
                if (quickNoteReturnsToDashboard) {
                    showStartScreen = true
                } else {
                    currentScreen = Screen.NOG_ORGANISEREN
                }
                quickNoteReturnsToDashboard = false
                selectedGedachte = null
            },
            onSave = { savedNote, savedStrokes ->
                if (selectedGedachte != null) {
                    QuickNoteRepository.update(
                        id = selectedGedachte!!.id,
                        text = savedNote,
                        inkStrokes = savedStrokes
                    )
                } else {
                    QuickNoteRepository.add(savedNote, inkStrokes = savedStrokes)
                }
            },
            onDelete = {
                if (selectedGedachte != null) {
                    QuickNoteRepository.delete(selectedGedachte!!.id)
                }
            },
            onClear = {
                if (quickNoteReturnsToDashboard) {
                    showStartScreen = true
                }
            }
        )
        return
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CcMidnight)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
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
                ,
                onOpenGedachte = { gedachte ->
                    selectedGedachte = gedachte
                    showQuickNoteScreen = true
                }
            )
        }

        CcRememberButton(
            onClick = {
                showQuickNoteScreen = true
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(40.dp)
        )
    }
}