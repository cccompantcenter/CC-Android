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
import com.cc.commandcenter.model.GedachteProcessingStatus
import com.cc.commandcenter.model.Screen
import com.cc.commandcenter.screens.GedachtenScreen
import com.cc.commandcenter.screens.GedachteProcessingScreen
import com.cc.commandcenter.screens.CardDetailScreen
import com.cc.commandcenter.screens.MainContent
import com.cc.commandcenter.screens.NewCardScreen
import com.cc.commandcenter.screens.QuickNoteScreen
import com.cc.commandcenter.screens.StartScreen
import com.cc.commandcenter.ui.theme.CcMidnight

@Composable
fun CCApp() {
    var showStartScreen by remember { mutableStateOf(true) }
    var showQuickNoteScreen by remember { mutableStateOf(false) }
    var showGedachtenScreen by remember { mutableStateOf(false) }
    var showGedachteProcessingScreen by remember { mutableStateOf(false) }
    var workflowCardId by remember { mutableStateOf<Long?>(null) }
    var currentScreen by remember { mutableStateOf(Screen.TODAY) }
    var quickNoteReturnsToDashboard by remember { mutableStateOf(false) }
    var selectedGedachte by remember { mutableStateOf<com.cc.commandcenter.model.QuickNote?>(null) }

    val cards = remember {
        mutableStateListOf(
            *CardRepository.getAllCards().toTypedArray()
        )
    }

    fun refreshCards() {
        cards.clear()
        cards.addAll(CardRepository.getAllCards())
    }

    fun saveCard(updatedCard: Card) {
        CardRepository.updateCard(updatedCard)
        refreshCards()
    }

    fun createCard(
        title: String,
        category: CardCategory,
        priority: CardPriority,
        dueDate: String,
        originalGedachteId: Long? = null,
        originalGedachtePreview: String? = null
    ): Card {
        val newCard = CardRepository.createCard(
            title = title,
            category = category,
            priority = priority,
            dueDate = dueDate,
            originalGedachteId = originalGedachteId,
            originalGedachtePreview = originalGedachtePreview
        )

        refreshCards()
        return newCard
    }

    fun deleteCard(card: Card) {
        CardRepository.deleteCard(card.id)
        refreshCards()
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
            isExisting = selectedGedachte != null,
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
                        source = selectedGedachte!!.source,
                        inkStrokes = savedStrokes
                    )
                } else {
                    QuickNoteRepository.add(savedNote, inkStrokes = savedStrokes)
                }
            },
            onDelete = {
                if (selectedGedachte != null) {
                    QuickNoteRepository.archive(selectedGedachte!!.id)
                }
            },
            onClear = {
                if (quickNoteReturnsToDashboard) {
                    showStartScreen = true
                }
            },
            onProcess = if (selectedGedachte != null) {
                {
                    showQuickNoteScreen = false
                    showGedachteProcessingScreen = true
                }
            } else {
                null
            }
        )
        return
    }

    if (showGedachteProcessingScreen) {
        val gedachte = selectedGedachte
        if (gedachte == null) {
            showGedachteProcessingScreen = false
        } else {
            GedachteProcessingScreen(
                gedachte = gedachte,
                onBack = {
                    showGedachteProcessingScreen = false
                    showQuickNoteScreen = true
                },
                onMaakCard = {
                    QuickNoteRepository.updateProcessingStatus(gedachte.id, GedachteProcessingStatus.PROCESSING)
                    val workflowCard = CardRepository.createCardFromGedachte(
                        gedachteId = gedachte.id,
                        gedachteTitle = gedachte.toCardTitle(),
                        gedachtePreview = gedachte.toCardTitle()
                    )
                    QuickNoteRepository.markConverted(gedachte.id)
                    showGedachteProcessingScreen = false
                    workflowCardId = workflowCard.id
                },
                onBewaarAlsGedachte = {
                    QuickNoteRepository.updateProcessingStatus(gedachte.id, GedachteProcessingStatus.NEW)
                    showGedachteProcessingScreen = false
                    currentScreen = Screen.NOG_ORGANISEREN
                    selectedGedachte = null
                },
                onArchiveer = {
                    QuickNoteRepository.archive(gedachte.id)
                    showGedachteProcessingScreen = false
                    currentScreen = Screen.NOG_ORGANISEREN
                    selectedGedachte = null
                }
            )
        }
        return
    }

    if (workflowCardId != null) {
        val workflowCard = CardRepository.getCardById(workflowCardId!!)
        if (workflowCard == null) {
            workflowCardId = null
        } else {
            CardDetailScreen(
                card = workflowCard,
                onBack = {
                    workflowCardId = null
                    showGedachteProcessingScreen = true
                },
                onDelete = {
                    deleteCard(workflowCard)
                    selectedGedachte?.let {
                        QuickNoteRepository.updateProcessingStatus(it.id, GedachteProcessingStatus.NEW)
                    }
                    workflowCardId = null
                    showGedachteProcessingScreen = true
                },
                onSave = { updatedCard ->
                    saveCard(updatedCard)
                    workflowCardId = null
                    currentScreen = Screen.TODAY
                    selectedGedachte = null
                }
            )
        }
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

private fun com.cc.commandcenter.model.QuickNote.toCardTitle(): String {
    return text.lines()
        .map { it.trim() }
        .firstOrNull { it.isNotEmpty() }
        ?: "Nieuwe Card"
}