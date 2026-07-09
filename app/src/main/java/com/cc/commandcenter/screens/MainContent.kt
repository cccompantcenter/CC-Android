package com.cc.commandcenter.screens
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cc.commandcenter.components.CardPlaceholder
import com.cc.commandcenter.components.CcCard
import com.cc.commandcenter.model.Card
import com.cc.commandcenter.model.CardCategory
import com.cc.commandcenter.model.CardPriority
import com.cc.commandcenter.model.CardStatus
import com.cc.commandcenter.model.Screen
import com.cc.commandcenter.subtitleFor
import com.cc.commandcenter.ui.theme.CcMuted
import com.cc.commandcenter.ui.theme.CcText
import java.time.LocalDate

@Composable
fun MainContent(
    screen: Screen,
    cards: List<Card>,
    onSaveCard: (Card) -> Unit,
    onDeleteCard: (Card) -> Unit,
    onCreateCard: (
        title: String,
        category: CardCategory,
        priority: CardPriority,
        dueDate: String
    ) -> Card,
    onOpenGedachte: (com.cc.commandcenter.model.QuickNote) -> Unit = {}
) {
    var selectedCardId by remember { mutableStateOf<Long?>(null) }
    var pendingNewCardIds by remember { mutableStateOf(emptySet<Long>()) }

    fun startCreatingCard(initialCategory: CardCategory) {
        val newCard = onCreateCard(
            "Nieuwe Card",
            initialCategory,
            CardPriority.NORMAL,
            LocalDate.now().toString()
        )
        pendingNewCardIds = pendingNewCardIds + newCard.id
        selectedCardId = newCard.id
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(36.dp))
            .background(Color(0xFF15130F))
            .padding(32.dp)
    ) {
        val activeCard = selectedCardId?.let { id -> cards.firstOrNull { it.id == id } }

        if (activeCard == null) {
            Text(
                text = screen.title,
                color = CcText,
                fontSize = 38.sp,
                fontWeight = FontWeight.Light
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = subtitleFor(screen),
                color = CcMuted,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(32.dp))
        }

        when {
            activeCard != null -> {
                CardDetailScreen(
                    card = activeCard,
                    autoFocusNotes = pendingNewCardIds.contains(activeCard.id),
                    onBack = {
                        if (pendingNewCardIds.contains(activeCard.id) && isEmptyDraftCard(activeCard)) {
                            onDeleteCard(activeCard)
                            pendingNewCardIds = pendingNewCardIds - activeCard.id
                        }
                        selectedCardId = null
                    },
                    onDelete = {
                        onDeleteCard(activeCard)
                        pendingNewCardIds = pendingNewCardIds - activeCard.id
                        selectedCardId = null
                    },
                    onSave = { updatedCard ->
                        val wasPendingNewCard = pendingNewCardIds.contains(updatedCard.id)
                        onSaveCard(updatedCard)
                        pendingNewCardIds = pendingNewCardIds - updatedCard.id
                        selectedCardId = if (wasPendingNewCard) null else updatedCard.id
                    }
                )
            }

            else -> {
                when (screen) {
                    Screen.TODAY -> TodayScreen(
                        cards = cards,
                        onCardClick = { selectedCardId = it.id },
                        onAddCard = { startCreatingCard(CardCategory.MY_TASKS) }
                    )

                    Screen.NOG_ORGANISEREN -> GedachtenScreen(onOpenGedachte = onOpenGedachte)

                    Screen.FOCUS -> FocusScreen(
                        cards = cards,
                        onCardClick = { selectedCardId = it.id }
                    )

                    Screen.MY_TASKS -> CardPlaceholder("Mijn taken", "Hier komen straks jouw eigen Cards.")
                    Screen.WAITING -> {
                        WaitingCardsScreen(
                            cards = cards,
                            onCardClick = { selectedCardId = it.id }
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        androidx.compose.material3.Button(
                            onClick = { startCreatingCard(CardCategory.WAITING) }
                        ) {
                            Text("Nieuwe Card")
                        }
                    }
                    Screen.OTHERS -> CardPlaceholder("Taken van anderen", "Hier komen Cards die bij een ander liggen.")
                    Screen.IDEAS -> CardPlaceholder("Ideeën", "Hier bewaar je losse gedachten voordat ze actie worden.")
                    Screen.ARCHIVE -> CardPlaceholder("Archief", "Hier komen afgeronde of bewaarde Cards.")
                }
            }
        }
    }
}

private fun isEmptyDraftCard(card: Card): Boolean {
    val title = card.title.trim()
    return (title.isBlank() || title == "Nieuwe Card") &&
        card.description.isBlank() &&
        card.notes.isBlank() &&
        card.tags.isEmpty() &&
        !card.favorite &&
        card.priority == CardPriority.NORMAL &&
        card.status == CardStatus.OPEN
}

@Composable
private fun WaitingCardsScreen(
    cards: List<Card>,
    onCardClick: (Card) -> Unit
) {
    val waitingCards = cards.filter {
        it.category == CardCategory.WAITING
    }

    if (waitingCards.isEmpty()) {
        CardPlaceholder(
            "Reactie afwachten",
            "Hier komen Cards waarbij je wacht op iemand anders."
        )
        return
    }

    Column(
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(16.dp)
    ) {
        waitingCards.forEach { card ->
            CcCard(
                card = card,
                onToggleStatus = { },
                onClick = {
                    onCardClick(card)
                }
            )
        }
    }
}